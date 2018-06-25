package com.haitai.haitaitv.component.jfinal.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ModelBind {

    /**
     * 表名
     */
    String table();

    /**
     * 主键
     */
    String key() default "id";

    /**
     * 数据库名称
     */
    String configName() default "main";
}
