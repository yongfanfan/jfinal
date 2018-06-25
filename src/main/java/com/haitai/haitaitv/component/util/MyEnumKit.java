package com.haitai.haitaitv.component.util;

import org.beetl.sql.core.kit.EnumKit;

/**
 * @author liuzhou
 *         create at 2017-03-08 14:39
 */
public class MyEnumKit {

    private MyEnumKit() {
    }

    // 约定枚举类型使用Integer类型的字段查找，数据库中可为tinyint、smallint、int
    public static Enum getEnumByValue(Class c, Object value) {
        if (value instanceof Number) {
            return EnumKit.getEnumByValue(c, ((Number) value).intValue());
        }
        if (value instanceof String) {
            return EnumKit.getEnumByValue(c, Integer.valueOf((String) value));
        }
        return null;
    }
}
