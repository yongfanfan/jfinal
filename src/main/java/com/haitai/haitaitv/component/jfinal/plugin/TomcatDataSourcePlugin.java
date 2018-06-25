package com.haitai.haitaitv.component.jfinal.plugin;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 本插件是作为Tomcat7 中自带数据源的插件
 * @author gentoo
 *
 */
public class TomcatDataSourcePlugin implements IPlugin, IDataSourceProvider
{
    /**
     * JDBC连接路径
     */
    private String jdbcUrl;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * 最大连接数
     */
    private int maxActive = 100;

    /**
     * 初始连接条数
     */
    private int initialSize = 10;

    /**
     * 最长等待时间
     */
    private int maxWait = 10000;

    /**
     * 最小空闲时间
     */
    private int minIdle = 10;

    /**
     * 默认的驱动名称
     */
    private String driverClass = "com.mysql.jdbc.Driver";

    /**
     * Tomcat中的JDBC数据源对象
     */
    private org.apache.tomcat.jdbc.pool.DataSource jdbcDatasources;

    public TomcatDataSourcePlugin(PoolProperties p)
    {
        jdbcDatasources = new org.apache.tomcat.jdbc.pool.DataSource();
        jdbcDatasources.setPoolProperties(p);

    }

    public TomcatDataSourcePlugin setDriverClass(String driverClass)
    {

        if (StrKit.isBlank(driverClass))
            throw new IllegalArgumentException("driverClass can not be blank.");
        this.driverClass = driverClass;
        return this;
    }

    public TomcatDataSourcePlugin(String jdbcUrl, String user, String password)
    {

        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    public TomcatDataSourcePlugin(String jdbcUrl, String user, String password, String driverClass)
    {

        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.driverClass = driverClass != null ? driverClass : this.driverClass;
    }

    public TomcatDataSourcePlugin(String jdbcUrl, String user, String password, String driverClass, Integer maxActive, Integer initialSize, Integer maxWait, Integer minIdle)
    {

        initTomcatDataSourcePlugin(jdbcUrl, user, password, driverClass, maxActive, initialSize, maxWait, minIdle);
    }

    private void initTomcatDataSourcePlugin(String jdbcUrl, String user, String password, String driverClass, Integer maxActive, Integer initialSize, Integer maxWait, Integer minIdle)
    {

        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.driverClass = driverClass != null ? driverClass : this.driverClass;
        this.maxActive = maxActive != null ? maxActive : this.maxActive;
        this.initialSize = initialSize != null ? initialSize : this.initialSize;
        this.maxWait = maxWait != null ? maxWait : this.maxWait;
        this.minIdle = minIdle != null ? minIdle : this.minIdle;
    }

    public TomcatDataSourcePlugin(Properties properties)
    {

        Properties ps = properties;
        initTomcatDataSourcePlugin(ps.getProperty("jdbcUrl"), ps.getProperty("user"), ps.getProperty("password"), ps.getProperty("driverClass"), toInt(ps.getProperty("maxActive")), toInt(ps.getProperty("initialSize")), toInt(ps.getProperty("maxWait")), toInt(ps.getProperty("minIdle")));
    }

    private Integer toInt(String str)
    {
        return Integer.parseInt(str);
    }

    public DataSource getDataSource()
    {

        return jdbcDatasources;
    }

    public boolean start()
    {
        PoolProperties p = new PoolProperties();
        p.setUrl(jdbcUrl);
        p.setDriverClassName(driverClass);
        p.setUsername(user);
        p.setPassword(password);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        p.setMinIdle(minIdle);
        p.setMaxActive(maxActive);
        p.setInitialSize(initialSize);
        p.setMaxWait(maxWait);

        jdbcDatasources = new org.apache.tomcat.jdbc.pool.DataSource();
        jdbcDatasources.setPoolProperties(p);

        return true;
    }

    public boolean stop()
    {
        if (jdbcDatasources != null)
        {
            jdbcDatasources.close();
        }
        return true;
    }

}
