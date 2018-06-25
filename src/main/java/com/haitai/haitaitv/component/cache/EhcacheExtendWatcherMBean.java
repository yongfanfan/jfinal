package com.haitai.haitaitv.component.cache;

/**
 * Created by liuzhou on 2016/12/22.
 */
public interface EhcacheExtendWatcherMBean {

    /**
     * 占用堆内存字节数
     */
    long getHeapBytes();

    /**
     * 获取所有的键
     */
    String printKeys();

    /**
     * 获取指定元素的信息
     */
    String printInfo(String key);

}
