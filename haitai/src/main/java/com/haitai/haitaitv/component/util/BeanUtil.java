package com.haitai.haitaitv.component.util;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import net.sf.cglib.beans.BeanMap;
import org.beetl.sql.core.Tail;

import java.util.HashMap;
import java.util.Map;

/**
 * bean工具类
 *
 * @author liuzhou
 *         create at 2017-04-02 16:49
 */
public class BeanUtil {

    private static final Map<Class, BeanMap> cache = new HashMap<>();

    /**
     * 从bean里获取指定属性
     *
     * @param bean 目前支持的类型：框架无关【javabean，Map】；beetlsql【Tail】；jfinal【Model，Record】
     * @param key  字段名
     * @return 字段对应的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object bean, String key) {
        return (T) getObject(bean, key);
    }

    private static Object getObject(Object bean, String key) {
        if (key == null) {
            return null;
        }
        if (bean instanceof Map) {
            return ((Map) bean).get(key);
        }
        if (bean instanceof Model) {
            return ((Model) bean).get(key);
        }
        if (bean instanceof Record) {
            return ((Record) bean).get(key);
        }
        BeanMap beanMap = getBeanMap(bean);
        Object value = beanMap.get(bean, key);
        if (value != null) {
            return value;
        }
        if (bean instanceof Tail) {
            value = ((Tail) bean).get(key);
        }
        return value;
    }

    private static BeanMap getBeanMap(Object bean) {
        Class clazz = bean.getClass();
        BeanMap result = cache.get(clazz);
        if (result != null) {
            return result.newInstance(bean);
        }
        synchronized (BeanUtil.class) {
            result = cache.get(clazz);
            if (result != null) {
                return result.newInstance(bean);
            }
            result = BeanMap.create(bean);
            cache.put(clazz, result);
            return result;
        }
    }
}
