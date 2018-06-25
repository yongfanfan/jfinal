package com.haitai.haitaitv.common.enumeration;

/**
 * 可以作为多个类的通用字段。表示删除属性等也可仿此枚举再建一个
 * Created by liuzhou on 2017/3/11.
 */
public enum AvailableEnum {
    DISABLE("禁用", 0), ENABLE("可用", 1);

    /**
     * 描述
     */
    private String description;
    /**
     * 对应数据库的值
     */
    private int value;

    AvailableEnum(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AvailableEnum{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}
