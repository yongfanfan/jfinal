package com.haitai.haitaitv.component.cache;

import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.component.jfinal.base.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息缓存
 */
public class UserCache extends BaseService {

    private final static Logger LOG = LogManager.getLogger(UserCache.class);
    private final static String cacheName = "UserCache";
    private static MyCache cache = null;

    private UserCache() {
    }

    public static void init() {
        if (cache == null) {
            cache = MyCacheKit.get(cacheName);
        }
        LOG.info("####用户Cache初始化......");
        Map<Integer, SysUser> cacheMap = new HashMap<Integer, SysUser>();
        List<SysUser> userList = sm.getMapper(SysUserDao.class).all();
        for (SysUser user : userList) {
            cacheMap.put(user.getId(), user);
        }
        cache.put("map", cacheMap);
    }

    public static SysUser getUser(Integer pid) {
        return getUserMap().get(pid);
    }

    public static Map<Integer, SysUser> getUserMap() {
        return cache.<Map<Integer, SysUser>>get("map").orElse(Collections.emptyMap());
    }

}
