package com.haitai.haitaitv.module.back.common;

/**
 * Created by liuzhou on 2017/8/9.
 */
public interface AfterSetterAdapter {

    /**
     * 可供子类添加自定义初始化逻辑，当执行AbstractController的getBean系列方法时会在set完毕后调用
     */
    default void afterSetter() {
    }

}
