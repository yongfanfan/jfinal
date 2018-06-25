package com.haitai.haitaitv.common.cache;

import com.haitai.haitaitv.common.entity.SysDictDetail;
import com.haitai.haitaitv.common.entity.SysLog;
import com.haitai.haitaitv.common.repository.SysDictDetailDao;
import com.haitai.haitaitv.component.cache.MyCache;
import com.haitai.haitaitv.component.cache.MyCacheKit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;


public class HtvCache {

    private static final Logger LOG = LogManager.getLogger(HtvCache.class);
    private static final MyCache HTV_CACHE = MyCacheKit.get("HtvCache");

    public static void init() {
        LOG.info("####缓存初始化开始......");
        // 系统常量
        HtvCache.updateCache();
        // 数据字典
        DictCache.init();
        // URL KEY初始化
        // 日志配置初始化
        SysLog.init();
        LOG.info("####缓存初始化结束......");
    }

    /**
     * 更新HtvCache缓存:headTitle,
     */
    public static void updateCache() {
        HTV_CACHE.removeAll();
        String headTitle;
        SysDictDetailDao dao = JFinalBeetlSql.dao().getMapper(SysDictDetailDao.class);
        SysDictDetail template = new SysDictDetail();
        template.setDictType("systemParam");
        template.setDetailCode("1");
        SysDictDetail headTitleDict = dao.templateOne(template);
        if (headTitleDict != null) {
            headTitle = headTitleDict.getDetailName();
        } else {
            headTitle = "海苔视购";
        }
        HTV_CACHE.put("headTitle", headTitle);

    }

    public static String getHeadTitle() {
        return HTV_CACHE.<String>get("headTitle").orElse("海苔视购");
    }

}
