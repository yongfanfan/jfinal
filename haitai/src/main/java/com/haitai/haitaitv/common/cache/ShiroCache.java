package com.haitai.haitaitv.common.cache;

import com.haitai.haitaitv.component.cache.MyCache;
import com.haitai.haitaitv.component.cache.MyCacheKit;

/**
 * shiro的cache管理
 *
 * @author liuzhou
 *         create at 2017-03-29 22:24
 */
public class ShiroCache {
    private static MyCache authorizationCache = MyCacheKit.get("authorizationCache");
    private static MyCache authenticationCache = MyCacheKit.get("authenticationCache");

    public static void clearCache() {
        authorizationCache.removeAll();
        authenticationCache.removeAll();
    }
}
