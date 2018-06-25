package com.haitai.haitaitv.component.mq;

import javax.jms.Message;

@FunctionalInterface
public interface MessageHandler {

    /**
     * 消息回调提供的调用方法
     *
     * @param message
     */
    void handle(Message message);

}
