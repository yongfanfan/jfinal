package com.haitai.haitaitv.component.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsProductHelper {
    public static final Logger LOG = LoggerFactory.getLogger(JmsProductHelper.class);
    private  MessageProducer producer;
    private  Session session;
    private  Connection connection;
    private ActiveMQSession mqsession;
    private ActiveMQConnection mqconn;
    
    public JmsProductHelper(MessageProducer producer, Session session, Connection connection){
        this.producer = producer;
        this.session = session;
        this.connection = connection;
    }
    
    public JmsProductHelper(MessageProducer producer, ActiveMQSession mqsession, ActiveMQConnection mqconn){
        this.producer = producer;
        this.mqsession = mqsession;
        this.mqconn = mqconn;
    }
    
    /**
     *  获取大文件session
     * @throws JMSException 
     */
    public ActiveMQSession getMqSession() throws JMSException{
        return this.mqsession;
    }
    
    /**
     *  获取session
     * @throws JMSException 
     */
    public Session getSession() throws JMSException{
        return this.session;
    }
    
    /**
     * 单例发送消息
     * @throws JMSException 
     */
    public void send(Message message) throws JMSException{
        this.producer.send(message);
        close();
    }
    
    /**
     * 关闭连接
     * @throws JMSException 
     */
    public void close() throws JMSException {
        if(producer != null){
            producer.close();
            producer = null;
        }
        if (session != null) {
            session.close();
            session = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}