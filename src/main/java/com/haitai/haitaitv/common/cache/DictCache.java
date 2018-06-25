package com.haitai.haitaitv.common.cache;

import com.haitai.haitaitv.common.entity.SysDictDetail;
import com.haitai.haitaitv.common.repository.SysDictDetailDao;
import com.haitai.haitaitv.component.cache.MyCache;
import com.haitai.haitaitv.component.cache.MyCacheKit;
import com.haitai.haitaitv.component.util.StrUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 数据字典缓存
 */
public class DictCache {

    private final static Logger log = LogManager.getLogger(DictCache.class);
    private static MyCache cache;

    /**
     * 初始化Map
     */
    public static void init() {
        if (cache == null) {
            cache = MyCacheKit.get("DictCache");
        }
        log.info("####数据字典Cache初始化......");
        DictCache.initDict();
    }

    /**
     * 初始化数据字典明细表
     */
    public static void initDict() {
        Map<Integer, SysDictDetail> dictMap = new LinkedHashMap<>();
        SysDictDetailDao dao = JFinalBeetlSql.dao().getMapper(SysDictDetailDao.class);
        List<SysDictDetail> listDetail = dao.template(new SysDictDetail());
        for (SysDictDetail detail : listDetail) {
            dictMap.put(detail.getId(), detail);
        }
        cache.put("map", dictMap);
    }

    public static Map<Integer, SysDictDetail> getCacheMap() {
        return cache.<Map<Integer, SysDictDetail>>get("map").orElse(null);
    }

    //////////////////////////jstl 标签/////////////////////////////

    /**
     * 获取下拉菜单，使用listSysDictDetailByType()方法取代
     */
    @Deprecated
    public static String getSelect(String type, Integer selected_value) {
        Map<Integer, SysDictDetail> map = DictCache.getCacheMap();
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer key : map.keySet()) {
            SysDictDetail dict = map.get(key);
            if (dict.getDictType().equals(type)) {
                sb.append("<option value=\"");
                sb.append(dict.getId());
                sb.append("\" ");
                sb.append(key.equals(selected_value) ? "selected" : "");
                sb.append(">");
                sb.append(dict.getDetailName());
                sb.append("</option>");
            }
        }
        return sb.toString();
    }

    public static List<SysDictDetail> listSysDictDetailByType(String type) {
        Map<Integer, SysDictDetail> map = DictCache.getCacheMap();
        if (type == null || map == null || map.isEmpty()) {
            return Collections.emptyList();
        }
        return map.values().stream()
                .filter(sysDictDetail -> type.equals(sysDictDetail.getDictType()))
                .collect(Collectors.toList());
    }

    /**
     * 获取Value值
     *
     * @param key
     * @return
     */
    public static String getValue(Integer key) {
        if (key == null) {
            return null;
        }
        SysDictDetail dict = getCacheMap().get(key);
        return dict == null ? null : dict.getDetailName();
    }

    /**
     * 获取Code值
     *
     * @param key
     * @return
     */
    public static String getCode(Integer key) {
        if (key == null) {
            return null;
        }
        SysDictDetail dict = getCacheMap().get(key);
        return dict == null ? null : dict.getDetailCode();
    }


    /**
     * 获取下拉菜单 code:value形式
     *
     * @param type
     * @param selected_code
     * @return
     */
    public static String getSelectByCode(String type, String selected_code) {
        Map<Integer, SysDictDetail> map = DictCache.getCacheMap();
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer key : map.keySet()) {
            SysDictDetail dict = map.get(key);
            if (dict.getDictType().equals(type)) {
                String code = dict.getDetailCode();
                sb.append("<option value=\"");
                sb.append(code);
                sb.append("\" ");
                sb.append(StrUtil.isNotEmpty(code) && code.equals(selected_code) ? "selected" : "");
                sb.append(">");
                sb.append(dict.getDetailName());
                sb.append("</option>");
            }
        }
        return sb.toString();
    }

    /**
     * 通过type和code获取Value值
     *
     * @param type
     * @param code
     * @return
     */
    public static String getValueByCode(String type, String code) {
        if (type == null || code == null) {
            return null;
        }
        Map<Integer, SysDictDetail> map = getCacheMap();
        for (SysDictDetail dict : map.values()) {
            if (code.equals(dict.getDetailCode()) && type.equals(dict.getDictType())) {
                return dict.getDetailName();
            }
        }
        return null;
    }

}
