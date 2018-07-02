package com.haitai.haitaitv.common.cache;

import com.haitai.haitaitv.component.cache.MyCache;
import com.haitai.haitaitv.component.cache.MyCacheKit;
import com.haitai.haitaitv.component.util.StrUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

/**
 * 前端用户鉴权缓存
 *
 * @author liuzhou
 *         create at 2017-03-26 20:39
 */
public class AccessCache {

    private static final MyCache cache = MyCacheKit.get("AccessCache");

    private AccessCache() {
    }

    

    public static void clearCache() {
        cache.removeAll();
    }

}
