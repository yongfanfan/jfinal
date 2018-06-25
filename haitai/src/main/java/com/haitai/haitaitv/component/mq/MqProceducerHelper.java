package com.haitai.haitaitv.component.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;

/**
 * 用于持有MqProceducer中不应该作为实例变量的数据，使得同一个MqProceducer实例可以向多个queue发送数据
 *
 * @author liuzhou
 *         create at 2017-07-26 14:02
 */
public class MqProceducerHelper {

    private static final Logger LOG = LogManager.getLogger(MqProceducerHelper.class);

    private Connection connection;

    private Session session;

    private MessageProducer producer;

    private Message message;

    public MqProceducerHelper(Connection connection, Session session, MessageProducer producer, Message message) {
        this.connection = connection;
        this.session = session;
        this.producer = producer;
        this.message = message;
    }

    /**
     * 此方法调用后，就不应该再使用相同实例调用其他方法
     */
    public void send() throws JMSException {
        producer.send(message);
        destroy();
    }

    /**
     * 此方法调用后，就不应该再使用相同实例调用其他方法
     */
    public void send(Message message) throws JMSException {
        producer.send(message);
        destroy();
    }

    public Session getSession() {
        return session;
    }

    private void destroy() {
        producer = null;
        closeSession(session);
        closeConnection(connection);
    }

    private void closeSession(Session session) {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            LOG.error("mq关闭session出错", e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            LOG.error("mq关闭connection出错", e);
        }
    }
}
