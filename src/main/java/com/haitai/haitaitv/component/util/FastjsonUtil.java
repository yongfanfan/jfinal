package com.haitai.haitaitv.component.util;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * fastjson工具类
 *
 * @author liuzhou
 *         create at 2017-08-28 17:12
 */
public class FastjsonUtil {

    /**
     * 序列化时，将tails字段与id字段排除在外
     */
    public static PropertyFilter propertyFilterTailsAndId =
            (object, name, value) -> !name.equals("tails") && !name.equals("id");

    /**
     * 序列化时，将tails字段排除在外
     */
    public static PropertyFilter propertyFilterTails = (object, name, value) -> !name.equals("tails");
}
