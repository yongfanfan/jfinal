package com.haitai.haitaitv.component.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author liuzhou
 *         create at 2016-12-22 15:42
 */
public class EhcacheExtendWatcher implements EhcacheExtendWatcherMBean {
    private Ehcache ehcache;

    public EhcacheExtendWatcher(Ehcache ehcache) {
        this.ehcache = ehcache;
    }

    @Override
    public long getHeapBytes() {
        return ehcache.getStatistics().getLocalHeapSizeInBytes();
    }

    @Override
    public String printKeys() {
        return ehcache.getKeys().toString();
    }

    @Override
    public String printInfo(String key) {
        Element element = ehcache.getQuiet(key);
        if (element == null) {
            return "不存在";
        }
        return "存在";
    }


}