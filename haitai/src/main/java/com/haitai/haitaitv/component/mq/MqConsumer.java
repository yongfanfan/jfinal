package com.haitai.haitaitv.component.mq;

import java.util.UUID;

import com.haitai.haitaitv.component.constant.ConfigConsts;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;

import javax.jms.*;

public class MqConsumer implements ExceptionListener {
    // 队列预取策略
    private int queuePrefetch = DEFAULT_QUEUE_PREFETCH;
    public final static int DEFAULT_QUEUE_PREFETCH = 10;

    private String brokerUrl;

    private String userName;

    private String password;

    private MessageListener messageListener;

    private Connection connection;

    private Session session;
    
    private ConnectionFactory connectionFactory;
    // 队列名
    private String queue;
    private static MqConsumer unique = new MqConsumer();

    private MqConsumer() {
    }

    /**
     * 注意【现在都是一条线程取用，所以没问题，但如果存在多条线程同时操作的话将会出现问题】
     * 只能单线程跑
     */
    public static MqConsumer getInstance() {
        return unique;
    }
    
    private Connection connect() throws JMSException{
        if (ConfigConsts.SERVER_INDEPENDENT) {
            // 独立模式，不启用mq
            return null;
        }
        // ActiveMQ的连接工厂
        connectionFactory = new ActiveMQConnectionFactory(this.userName, this.password, this.brokerUrl);
        connection = connectionFactory.createConnection();
        
        // activeMQ预取策略
        ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setQueuePrefetch(queuePrefetch);
        ((ActiveMQConnection) connection).setPrefetchPolicy(prefetchPolicy);
        connection.setExceptionListener(this);
        return connection;
    }
    
    /**
     * 执行消息获取的操作
     * 订阅模式
     *一对多
     * @throws Exception
     */
    public void startTopic() throws Exception {
        connection = this.connect();
        if(connection == null){
            return;
        }
        String clientId = UUID.randomUUID().toString();
        //持久订阅需要设置这个，据说，负载情况下每个客户端id要不同，不然会报错
        connection.setClientID(clientId);
        connection.start();
        // 会话采用非事务级别，消息到达机制使用自动通知机制
        session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(this.queue);    
        //持久订阅方式，不会漏掉信息  
        TopicSubscriber subs=session.createDurableSubscriber(topic, clientId);  
        subs.setMessageListener(this.messageListener);
        //非持久订阅方式  
//        MessageConsumer consumer = session.createConsumer(topic);    
//        consumer.setMessageListener(new MessageListener() {    
//            public void onMessage(Message message) {    
//                TextMessage tm = (TextMessage) message;    
//                try {    
//                    System.out.println("Received message: " + tm.getText());    
//                } catch (JMSException e) {    
//                    e.printStackTrace();    
//                }    
//            }    
//        });   
    }

    /**
     * 执行消息获取的操作
     * 点对点
     *
     * @throws Exception
     */
    public void start() throws Exception {
        connection = this.connect();
        if(connection == null){
            return;
        }
        connection.start();
        // 会话采用非事务级别，消息到达机制使用自动通知机制
        session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(this.queue);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this.messageListener);
    }

    /**
     * 关闭连接
     */
    public void shutdown() {
        try {
            if (session != null) {
                session.close();
                session = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onException(JMSException e) {
        e.printStackTrace();
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public int getQueuePrefetch() {
        return queuePrefetch;
    }

    public void setQueuePrefetch(int queuePrefetch) {
        this.queuePrefetch = queuePrefetch;
    }

}
