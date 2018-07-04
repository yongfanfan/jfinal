package com.haitai.haitaitv.component.jms;

import java.util.UUID;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.blob.BlobTransferPolicy;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.JmsConsts;

import javax.jms.*;

public class JmsConsumer implements ExceptionListener {
    /**
     * 队列预取策略
     * <p>
     * ActiveMQ 在发送一些消息之后，开启2个消费者去处理消息。会发现一个消费者处理了所有的消息，
     * 另一个消费者根本没收到消息。原因在于ActiveMQ的prefetch机制。当消费者去获取消息时，不会一条一条去获取，
     * 而是一次性获取一批，默认是1000条。这些预获取的消息，在还没确认消费之前，在管理控制台还是可以看见这些消息的，
     * 但是不会再分配给其他消费者，此时这些消息的状态应该算作“已分配未消费”，如果消息最后被消费，则会在服务器端被删除，
     * 如果消费者崩溃，则这些消息会被重新分配给新的消费者。但是如果消费者既不消费确认，又不崩溃，那这些消息就永远躺在消费者
     * 的缓存区里无法处理。更通常的情况是，消费这些消息非常耗时，你开了10个消费者去处理，结果发现只有一台机器吭哧吭哧处理，另外9台啥事不干。
     * <p>
     * 解决方案：将prefetch设为1，每次处理1条消息，处理完再去取，这样也慢不了多少。
     */
    private int queuePrefetch = DEFAULT_QUEUE_PREFETCH;
    public final static int DEFAULT_QUEUE_PREFETCH = 10;
    private boolean isRunning = true;

    private String brokerUrl = JmsConsts.NEIWANG_MQ_URL;

    private String userName = JmsConsts.NEIWANG_MQ_USER;

    private String password = JmsConsts.NEIWANG_MQ_PASSWORD;

    private MessageListener messageListener;

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    private Connection connection;

    private Session session;

    private ConnectionFactory connectionFactory;
    /**
     * 队列名
     */
    private String queue;
    private String queueName;
    private String queueId;

    private JmsConsumer(String queueName, String queueId) {
        this.queueName = queueName;
        this.queueId = queueId;
        this.queue = queueName + "_" + queueId;
    }

    /**
     * 静态工厂构造器
     *
     * @param queueName
     * @param queueId
     * @return
     */
    public static JmsConsumer of(String queueName, String queueId) {
        return new JmsConsumer(queueName, queueId);
    }

    /**
     * 采用监听模式来获取消息
     *
     * @throws Exception
     */
    public void start() throws Exception {
        connection = this.connect();
        if (connection == null || !JmsConsts.JMS_ENABLED) {
            return;
        }
        connection.start();
        // 会话采用非事务级别，消息到达机制使用自动通知机制
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(this.queue);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this.messageListener);
    }

    /**
     * 采用循环获取的方式来获取消息
     *
     * @throws Exception
     */
    public void receive() throws Exception {
//        connection = this.connect();
//        if (connection == null || !JmsConsts.JMS_ENABLED) {
//            return;
//        }
//        connection.start();
//        // 会话采用非事务级别，消息到达机制使用自动通知机制
//        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
//        Destination destination = session.createQueue(this.queue);
//        MessageConsumer consumer = session.createConsumer(destination);
//        JmsEventHandler eventHandler = new JmsEventHandler();
//
//        POOL.execute(() -> {
//            while (isRunning) {
//                try {
//                    Message message = consumer.receive();
//                    Thread.sleep(50);
//                    eventHandler.eventHandler(message);
//                } catch (InterruptedException | JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    /**
     * 执行消息获取的操作
     * 订阅模式
     * 一对多
     *
     * @throws Exception
     */
    public void startTopic() throws Exception {
        connection = this.connect();
        if (connection == null || !JmsConsts.JMS_ENABLED) {
            return;
        }
        String clientId = UUID.randomUUID().toString();
        //持久订阅需要设置这个，据说，负载情况下每个客户端id要不同，不然会报错
        connection.setClientID(clientId);
        connection.start();
        // 会话采用非事务级别，消息到达机制使用自动通知机制
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(this.queue);
        //持久订阅方式，不会漏掉信息
        TopicSubscriber subs = session.createDurableSubscriber(topic, clientId);
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

    private Connection connect() throws JMSException {
        // ActiveMQ的连接工厂
        connectionFactory = new ActiveMQConnectionFactory(this.userName, this.password, this.brokerUrl);
        ((ActiveMQConnectionFactory) connectionFactory).setUseAsyncSend(true);
        BlobTransferPolicy blob = new BlobTransferPolicy();
        blob.setUploadUrl("http://192.168.1.139:8181/fileserver/");
        ((ActiveMQConnectionFactory) connectionFactory).setBlobTransferPolicy(blob);
        connection = connectionFactory.createConnection();
        // activeMQ预取策略
        ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setQueuePrefetch(queuePrefetch);
        ((ActiveMQConnection) connection).setPrefetchPolicy(prefetchPolicy);
        connection.setExceptionListener(this);
        return connection;
    }

    /**
     * 注意【现在都是一条线程取用，所以没问题，但如果存在多条线程同时操作的话将会出现问题】
     * 只能单线程跑
     */
//    public static JmsConsumer getInstance() {
//        return unique;
//    }

    /**
     * 关闭连接
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        isRunning = false;
        if (session != null) {
            session.close();
            session = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    @Override
    public void onException(JMSException e) {
        e.printStackTrace();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JmsConsumer)) {
            return false;
        }
        return ((JmsConsumer) obj).getQueue().equalsIgnoreCase(queue);
    }

    public String getQueue() {
        return queue;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

}