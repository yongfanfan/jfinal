package com.haitai.haitaitv.component.mq;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * 由于java自带的BiFunction的apply不能抛异常，因此自定义一个函数式接口
 * Created by liuzhou on 2017/7/26.
 */
@FunctionalInterface
public interface MessageProducerFunction {

    MessageProducer apply(Session s, String q) throws JMSException;

}
