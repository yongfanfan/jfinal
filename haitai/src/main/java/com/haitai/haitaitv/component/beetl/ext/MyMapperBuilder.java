package com.haitai.haitaitv.component.beetl.ext;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.mapper.DefaultMapperBuilder;

import java.lang.reflect.Proxy;

/**
 * 扩展dao，使其支持自定义实现
 *
 * @author liuzhou
 *         create at 2017-03-25 22:32
 */
public class MyMapperBuilder extends DefaultMapperBuilder {
    /**
     * The Constructor.
     *
     * @param sqlManager the sql manager
     */
    public MyMapperBuilder(SQLManager sqlManager) {
        super(sqlManager);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T buildInstance(Class<T> mapperInterface) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //使用ContextLoader，适合大多数框架
        return (T) Proxy.newProxyInstance(loader == null ? this.getClass().getClassLoader() : loader,
                new Class<?>[]{mapperInterface},
                new MyMapperJavaProxy(this, sqlManager, mapperInterface));
    }
}
