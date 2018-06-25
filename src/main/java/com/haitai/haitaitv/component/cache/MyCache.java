package com.haitai.haitaitv.component.cache;

import java.util.Optional;
import java.util.function.Supplier;


/**
 * Created by liuzhou on 2017/3/5.
 */
public interface MyCache {

    /**
     * 取出缓存中的值，该值被Optional包裹
     *
     * @param key 键
     * @return Optional包裹后的值
     */
    <T> Optional<T> get(Object key);

    /**
     * 取出缓存中的值，若值为空则使用other提供的值，并且将值放入缓存
     *
     * @param key   键
     * @param other 值为空时的提供者
     * @return 值，可能为null
     */
    <T> T orElseGet(Object key, Supplier<? extends T> other);

    /**
     * 将指定键值放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功放入缓存
     */
    boolean put(Object key, Object value);

    /**
     * 将指定键移除缓存，并且返回移除前的值
     *
     * @param key 键
     * @return 键对应的值，可能为null
     */
    <T> T remove(Object key);

    /**
     * 清空缓存
     */
    void removeAll();

}
