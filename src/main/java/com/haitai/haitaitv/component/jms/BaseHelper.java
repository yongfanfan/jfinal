package com.haitai.haitaitv.component.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.JmsConsts;

public class BaseHelper {
    /**
     * 设置连接的最大连接数
     */
    public final static int DEFAULT_MAX_CONNECTIONS = 10;
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    /**
     * 设置每个连接中使用的最大活动会话数
     */
    private int maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION;
    private final static int DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION = 300;
    /**
     * 线程池数量
     */
    private final static int DEFAULT_THREAD_POOL_SIZE = 10;
    /**
     * 强制使用同步返回数据的格式
     */
    private final static boolean DEFAULT_USE_ASYNC_SEND_FOR_JMS = true;
    /**
     * 是否持久化消息
     */
    private final static boolean DEFAULT_IS_PERSISTENT = true;
    /**
     * 连接地址
     */
    private String brokerUrl;

    private String userName;

    private String password;

    private PooledConnectionFactory pooledConnectionFactory;
    private ActiveMQConnectionFactory connectionFactory;

    public BaseHelper() {
        this(DEFAULT_MAX_CONNECTIONS, DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION);
    }

    public BaseHelper(int maxConnections, int maximumActiveSessionPerConnection) {
        this.brokerUrl = JmsConsts.NEIWANG_MQ_URL+ "/";
        this.userName = JmsConsts.NEIWANG_MQ_USER;
        this.password = JmsConsts.NEIWANG_MQ_PASSWORD;
        this.maxConnections = maxConnections;
        this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
        init();
    }

    private void init() {
        // ActiveMQ的连接工厂
        connectionFactory = new ActiveMQConnectionFactory(this.userName, this.password, this.brokerUrl);
        connectionFactory.setUseAsyncSend(DEFAULT_USE_ASYNC_SEND_FOR_JMS);
//        BlobTransferPolicy blob = new BlobTransferPolicy();
//        blob.setUploadUrl(blobUrl);
//        blob.setDefaultUploadUrl(blobUrl);
//        connectionFactory.setBlobTransferPolicy(blob);
        // Active中的连接池工厂
        this.pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
        this.pooledConnectionFactory.setCreateConnectionOnStartup(true);
        this.pooledConnectionFactory.setMaxConnections(this.maxConnections);
        this.pooledConnectionFactory.setMaximumActiveSessionPerConnection(this.maximumActiveSessionPerConnection);
    }

    /**
     * 第一个参数是是否是事务型消息，设置为true,第二个参数无效
     * 第二个参数是
     * Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
     * Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
     * 时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
     * DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
     */
    public JmsProductHelper connectionQueue(String queue) throws JMSException {
        // 从连接池工厂中获取一个连接
        Connection connection = this.pooledConnectionFactory.createConnection();
        connection.start();
        // false 参数表示 为非事务型消息，后面的参数表示消息的确认类型
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queue);
        MessageProducer producer = session.createProducer(destination);
        // set delevery mode不设置，默认就是持久的
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        return new JmsProductHelper(producer, session, connection);
    }

    public JmsProductHelper connectionBlob(String queue) throws JMSException {
        // 从连接池工厂中获取一个连接
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        // false 参数表示 为非事务型消息，后面的参数表示消息的确认类型
        ActiveMQSession session = (ActiveMQSession) connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queue);
        MessageProducer producer = session.createProducer(destination);
        // set delevery mode不设置，默认就是持久的
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        return new JmsProductHelper(producer, session, connection);
    }

    public JmsProductHelper connectionTopic(String queue) throws JMSException {
        // 从连接池工厂中获取一个连接
        Connection connection = this.pooledConnectionFactory.createConnection();
        connection.start();
        // false 参数表示 为非事务型消息，后面的参数表示消息的确认类型
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(queue);
        MessageProducer producer = session.createProducer(destination);
        // set delevery mode不设置，默认就是持久的
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        return new JmsProductHelper(producer, session, connection);
    }
}
