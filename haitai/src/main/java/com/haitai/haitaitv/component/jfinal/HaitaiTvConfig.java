package com.haitai.haitaitv.component.jfinal;

import cn.dreampie.quartz.QuartzKey;
import cn.dreampie.quartz.QuartzPlugin;
import cn.dreampie.quartz.job.QuartzCronJob;
import com.haitai.haitaitv.common.cache.HtvCache;
import com.haitai.haitaitv.component.beetl.ext.MyBeanProcessor;
import com.haitai.haitaitv.component.beetl.ext.MyDebugInterceptor;
import com.haitai.haitaitv.component.beetl.ext.MyMapperBuilder;
import com.haitai.haitaitv.component.cache.MyCacheKit;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.OtherConsts;
import com.haitai.haitaitv.component.constant.operator.BfgdConsts;
import com.haitai.haitaitv.component.es.ElasticSearchUtil;
import com.haitai.haitaitv.component.jfinal.annotation.BackRoutes;
import com.haitai.haitaitv.component.jfinal.annotation.FrontRoutes;
import com.haitai.haitaitv.component.jfinal.base.BaseController;
import com.haitai.haitaitv.component.jfinal.base.BaseService;
import com.haitai.haitaitv.component.jfinal.handler.BasePathHandler;
import com.haitai.haitaitv.component.jfinal.handler.HtmlHandler;
import com.haitai.haitaitv.component.jfinal.handler.StaticResourceHandler;
import com.haitai.haitaitv.component.jfinal.log.Log4j2LogFactory;
import com.haitai.haitaitv.component.jfinal.plugin.TomcatDataSourcePlugin;
import com.haitai.haitaitv.component.shiro.ext.ShiroPlugin;
import com.haitai.haitaitv.component.util.StrUtil;
import com.jfinal.config.*;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;
import org.beetl.sql.core.DefaultSQLIdNameConversion;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.StringSqlTemplateLoader;
import org.beetl.sql.ext.SimpleCacheInterceptor;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import javax.sql.DataSource;
import javax.xml.ws.Endpoint;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuzhou on 2017/3/5.
 */
public class HaitaiTvConfig extends JFinalConfig {

    private Routes backRoutes;
    private Endpoint endPoint;

    public void configConstant(Constants constants) {
        PropKit.use("/conf/config.properties");
        constants.setDevMode(ConfigConsts.DEV_MODE);
        constants.setLogFactory(new Log4j2LogFactory());

        // beetl配置
        JFinal3BeetlRenderFactory renderFactory = new JFinal3BeetlRenderFactory();
        renderFactory.config();
        constants.setRenderFactory(renderFactory);
        // 注册自定义方法、标签等放进配置文件进行，以便后续有IDE插件后可被识别
        /*
        org.beetl.core.Configuration会被加载三次，分别是供beetl、beetlsql的SourceGen和beetlsql使用
        */

        // 配置后，renderError(401)之类的方法才能正常运作
        constants.setError401View(OtherConsts.PAGES_401);
        constants.setError403View(OtherConsts.PAGES_403);
        constants.setError404View(OtherConsts.PAGES_404);
        constants.setError500View(OtherConsts.PAGES_500);
    }

    public void configRoute(Routes routes) {
        // 提供给ShiroPlugin使用
        this.backRoutes = new BackRoutes();
        // 后端路由
        routes.add(this.backRoutes);
        // 前端路由
        routes.add(new FrontRoutes());
        // 其他控制器手动配置
    }

    public void configEngine(Engine engine) {
        // 使用beetl而不使用jfinal的template engine，故此处留空
    }

    public void configPlugin(Plugins plugins) {
        // 添加数据源，由于用了beetlsql，不再注册TomcatDataSourcePlugin，此处仅使用它提供数据源
        Prop prop = PropKit.use("/conf/db.properties");
        String db_type = prop.get("db_type", "mysql") + ".";
        String jdbcUrl = prop.get(db_type + "jdbcUrl");
        if (db_type.startsWith("sqlite")) {
            String DBPath = PathKit.getWebRootPath() + "/WEB-INF/";
            DBPath = StrUtil.replace(DBPath, "\\", "/");
            jdbcUrl = StrUtil.replaceOnce(jdbcUrl, "{webroot}", DBPath);
        }
        TomcatDataSourcePlugin tomcatDataSourcePlugin = new TomcatDataSourcePlugin(
                jdbcUrl, prop.get(db_type + "user"),
                prop.get(db_type + "password"),
                prop.get(db_type + "driverClass"));
        tomcatDataSourcePlugin.start();

        // 使用beetlsql作为dao工具。使用下划线命名转换，配置在config.properties中
        JFinalBeetlSql.init(tomcatDataSourcePlugin.getDataSource(), new DataSource[0]);
        SQLManager sqlManager = JFinalBeetlSql.dao();
        // 增加对LocalDate,LocalTime,LocalDateTime的支持
        sqlManager.setDefaultBeanProcessors(new MyBeanProcessor(sqlManager));
        // 设置缓存拦截器
        List<String> namespaces = Arrays.asList(PropKit.get("sql.require_cache", "").split(","));
        Interceptor cacheInterceptor = new SimpleCacheInterceptor(namespaces, MyCacheKit.getSimpleCacheInterCacheMamager());
        sqlManager.setInters(new Interceptor[]{cacheInterceptor});
        // 扩展dao，使其支持自定义实现
        sqlManager.setMapperBuilder(new MyMapperBuilder(sqlManager));
        /*// 无需再用注解标明查询参数名，需要java8并开启编译参数-parameters，现在官方已经直接支持
        sqlManager.getMapperConfig().getBuilder().setMethodDescBuilder(MethodDescExperimental::new);*/
        // 设置sqlId 到sql文件
        sqlManager.setSQLIdNameConversion(new DefaultSQLIdNameConversion());
        if (ConfigConsts.DEV_MODE) {
            // 开发模式下，将beetlsql也设置为开发模式
            sqlManager.getSqlLoader().setAutoCheck(true);
            sqlManager.getBeetl().getGroupTemplate()
                    .setResourceLoader(new StringSqlTemplateLoader(sqlManager.getSqlLoader(), true));
            // 设置debug拦截器
            sqlManager.setInters(new Interceptor[]{cacheInterceptor, new MyDebugInterceptor()});
        }
        // JFinalBeetlSql初始化后，才能初始化BaseController和BaseService
        BaseController.init();
        BaseService.init();

        // 添加定时任务quartz插件
        plugins.add(new QuartzPlugin());
        /*// 启用ehcache插件，不启用只是无法使用JFinal的CacheKit和无法使用JFinal的缓存查询而已
        plugins.add(new EhCachePlugin(MyCacheKit.getCacheManager()));*/
        // 启用shiro插件
        ShiroPlugin shiroPlugin = new ShiroPlugin(backRoutes);
        shiroPlugin.setLoginUrl("/admin/login");
        shiroPlugin.setSuccessUrl("/admin/home");
        shiroPlugin.setUnauthorizedUrl(OtherConsts.PAGES_403);
        plugins.add(shiroPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {
        // 异常拦截器，跳转到500页面，现在前后端的异常拦截器分离，在Routes中设置
        // interceptors.add(new BackExceptionInterceptor());
    }

    public void configHandler(Handlers handlers) {
        // 伪静态化，顺便处理shiro的一个bug
        handlers.add(new HtmlHandler());
        // 设置BASE_PATH
        handlers.add(new BasePathHandler());
        // 禁止对某些静态资源直接访问
        handlers.add(new StaticResourceHandler());
        // 跳过对webservice的请求处理
        //handlers.add(new UrlSkipHandler(".*/services.*", false));
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        HtvCache.init();
    }

    @Override
    public void beforeJFinalStop() {
        super.beforeJFinalStop();
        System.out.println("##################################");
        System.out.println("############系统正在停止##########");
        System.out.println("##################################");
    }
}
