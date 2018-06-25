package com.haitai.haitaitv.module.back.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.haitai.haitaitv.common.entity.StbConfig;
import com.haitai.haitaitv.common.entity.SysDepartment;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.StbConfigDao;
import com.haitai.haitaitv.common.repository.SysDepartmentDao;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.component.cache.MyCache;
import com.haitai.haitaitv.component.cache.MyCacheKit;
import com.haitai.haitaitv.component.jfinal.base.BaseService;
import com.haitai.haitaitv.component.util.StrUtil;

/**
 * 缓存一致性不高，故本类的方法原则上只应用于页面展示使用
 * 目前此类缓存采用默认设置，两分钟过期
 *
 * @author liuzhou
 *         create at 2017-04-05 17:41
 */
public class BackCacheService extends BaseService {

    public static final BackCacheService INSTANCE = new BackCacheService();
    private final MyCache cache = MyCacheKit.get("BackCacheService");

    private BackCacheService() {
    }
    /**
     * 使用getOperatorNameMap()获取指定operatorId的名称
     *
     * @param operatorId 可以是total和xxxTotal
     * @see #getOperatorNameMap()
     */
    public String getOperatorName(String operatorId) {
        if ("total".equals(operatorId)) {
            return "合计";
        }
        Map<String, String> opName = getOperatorNameMap();
        if (operatorId.endsWith("Total")) {
            return opName.get(operatorId.substring(0, operatorId.length() - 5)) + "合计";
        }
        return opName.get(operatorId);
    }

    public Map<String, String> getOperatorNameMap() {
        return cache.orElseGet("operatorNameMap", () -> {
            List<StbConfig> configs = sm.getMapper(StbConfigDao.class).all();
            return configs.stream().collect(Collectors.toMap(StbConfig::getOperatorId, StbConfig::getOperatorName));
        });
    }

    /**
     * 由于做修改后主动调用了#clearCacheOfDepartmentNameMap清缓存，此方法的一致性要稍好一些
     */
    public Map<Integer, String> getDepartmentNameMap() {
        return cache.orElseGet("departmentNameMap", () -> {
            List<SysDepartment> configs = sm.getMapper(SysDepartmentDao.class).all();
            return configs.stream().collect(Collectors.toMap(SysDepartment::getId, SysDepartment::getName));
        });
    }

    /**
     * 主动清除getDepartmentNameMap()方法的缓存
     */
    public void clearCacheOfDepartmentNameMap() {
        cache.remove("departmentNameMap");
    }

    public Map<Integer, String> getUserRealnameMap() {
        return cache.orElseGet("userRealNameMap", () -> {
            List<SysUser> users = sm.getMapper(SysUserDao.class).all();
            return users.stream().collect(Collectors.toMap(SysUser::getId, SysUser::getRealname));
        });
    }


    /**
     * 返回一个map，键为渠道号，值为对应的父渠道号（若渠道本身就是根渠道，值为它自己的渠道号）
     */
    public Map<String, String> getParentOperatorIdMap() {
        return cache.orElseGet("parentOperatorIdMap", () -> {
            List<StbConfig> list = sm.getMapper(StbConfigDao.class).all();
            return list.stream()
                    .collect(Collectors.toMap(StbConfig::getOperatorId,
                            config -> {
                                String result = config.getParentOperatorId();
                                if (StrUtil.isNotEmpty(result)) {
                                    return result;
                                }
                                return config.getOperatorId();
                            }));
        });
    }


    public void clearCache() {
        cache.removeAll();
    }
}
