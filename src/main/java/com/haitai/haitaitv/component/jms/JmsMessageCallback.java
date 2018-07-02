package com.haitai.haitaitv.component.jms;

import javax.jms.Message;

@FunctionalInterface
public interface JmsMessageCallback {

    /**
     * 消息回调提供的调用方法
     *
     * @param message
     */
    void apply(Message message);

}
