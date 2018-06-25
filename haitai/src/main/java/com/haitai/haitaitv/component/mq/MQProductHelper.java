package com.haitai.haitaitv.component.mq;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.haitai.haitaitv.component.constant.MqConsts;
import com.haitai.haitaitv.component.util.encryption.MD5Util;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class MQProductHelper {
    public static final Logger LOG = LoggerFactory.getLogger(MQProductHelper.class);
    private static PooledConnectionFactory poolFactory;

    /**
     * 获取单例的PooledConnectionFactory
     *
     * @return
     */
    private static synchronized PooledConnectionFactory getPooledConnectionFactory() {
        LOG.info("getPooledConnectionFactory");
        if (poolFactory != null)
            return poolFactory;
        LOG.info("getPooledConnectionFactory create new");
        String userName = MqConsts.ONEGOODS_MQ_USER;
        String password = MqConsts.ONEGOODS_MQ_PASSWORD;
        String url = MqConsts.ONEGOODS_MQ_URL;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                userName, password, url);
        poolFactory = new PooledConnectionFactory(factory);
        // 池中借出的对象的最大数目
        poolFactory.setMaxConnections(100);
        poolFactory.setMaximumActiveSessionPerConnection(50);
        // 后台对象清理时，休眠时间超过了3000毫秒的对象为过期
        poolFactory.setTimeBetweenExpirationCheckMillis(3000);
        LOG.info("getPooledConnectionFactory create success");
        return poolFactory;
    }

    /**
     * @return * @throws JMSException
     */
    public static Session createSession() throws JMSException {
        PooledConnectionFactory poolFactory = getPooledConnectionFactory();
        PooledConnection pooledConnection = (PooledConnection) poolFactory
                .createConnection();
        // false 参数表示 为非事务型消息，后面的参数表示消息的确认类型（见4.消息发出去后的确认模式）
        return pooledConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public static void produce(String subject, String msg) {
        LOG.info("producer send msg: {} ", msg);
        if (StringUtils.isEmpty(msg)) {
            LOG.warn("发送消息不能为空。");
            return;
        }
        try {
            Session session = createSession();
            LOG.info("create session");
            TextMessage textMessage = session.createTextMessage(msg);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(textMessage);
            LOG.info("create session success");
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }

   
}