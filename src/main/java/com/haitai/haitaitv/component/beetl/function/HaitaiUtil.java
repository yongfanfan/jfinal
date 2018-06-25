package com.haitai.haitaitv.component.beetl.function;

import com.haitai.haitaitv.common.cache.DictCache;
import com.haitai.haitaitv.component.constant.operator.IcntvConsts;
import com.haitai.haitaitv.component.constant.QiniuConsts;
import com.haitai.haitaitv.component.util.BeanUtil;
import com.haitai.haitaitv.component.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuzhou
 *         create at 2017-04-02 16:43
 */
public class HaitaiUtil extends StrUtil {

    /**
     * 获取bean的默认图片路径
     */
    public static String getImage(Object bean) {
        return getImage(bean, "imageNetUrl", "imageUrl");
    }

    /**
     * 当七牛启用时，才优先使用网络图片（否则只会使用本地图片）
     *
     * @param netUrlAttr 网络图片字段
     * @param urlAttr    本地图片字段
     */
    public static String getImage(Object bean, String netUrlAttr, String urlAttr) {
        String imageUrl = null;
        if (QiniuConsts.QINIU_ENABLED) {
            imageUrl = BeanUtil.get(bean, netUrlAttr);
        }
        if (StrUtil.isEmpty(imageUrl)) {
            imageUrl = BeanUtil.get(bean, urlAttr);
        } else if (IcntvConsts.FLAG) {
            // 启用七牛时，未来服务器需替换域名
            imageUrl = imageUrl.replaceAll("resource.tvcms.haitai.tv", "resource.a008.ottcn.com");
        }
        if (StrUtil.isNotEmpty(imageUrl)) {
            return imageUrl.replaceAll("\\\\", "\\/");
        }
        return null;
    }

    /**
     * 获取url属性
     *
     * @param netUrlAttr 网络url，优先使用
     * @param urlAttr    本地url
     */
    public static String getUrl(Object bean, String netUrlAttr, String urlAttr) {
        String url = BeanUtil.get(bean, netUrlAttr);
        if (StrUtil.isEmpty(url)) {
            url = BeanUtil.get(bean, urlAttr);
        }
        if (StrUtil.isNotEmpty(url)) {
            return url.replaceAll("\\\\", "\\/");
        }
        return "";
    }

    /**
     * 获取field的class
     *
     * @param field          列代表字段
     * @param orderField     当前排序字段
     * @param orderDirection 当前顺序
     */
    public static String orderClass(String field, String orderField, String orderDirection) {
        if (!field.equals(orderField)) {
            return "";
        }
        if ("desc".equalsIgnoreCase(orderDirection)) {
            return "desc";
        }
        return "asc";
    }

    public static String dictValue(Integer key) {
        return DictCache.getValue(key);
    }

    /**
     * 返回object是否包含于objects，使用时需要注意应传入一致类型的参数
     */
    public static boolean contain(Object object, Object... objects) {
        List<Object> list = Arrays.asList(objects);
        return list.contains(object);
    }

    /**
     * @param map       键值关系
     * @param concatKey 以逗号连接的多个键
     * @return 以逗号连接的多个值
     */
    public static String concatGet(Map<String, String> map, String concatKey) {
        if (isEmpty(concatKey)) {
            return "";
        }
        String[] keys = concatKey.split(",");
        return Arrays.stream(keys).map(map::get).collect(Collectors.joining(","));
    }

    /**
     * 将秒数转化成"hh:mm:ss"
     */
    public static String seconds2Hms(Integer duration) {
        if (duration == null || duration <= 0) {
            return "00:00:00";
        }
        int temp = duration;
        int seconds = temp % 60;
        temp = duration / 60;
        int minutes = temp % 60;
        int hours = temp / 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
