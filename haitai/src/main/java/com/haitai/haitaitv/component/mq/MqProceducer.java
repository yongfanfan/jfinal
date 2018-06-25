package com.haitai.haitaitv.component.mq;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.util.UploadUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MqProceducer implements ExceptionListener {
    // 设置连接的最大连接数
    public final static int DEFAULT_MAX_CONNECTIONS = 10; //5
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    // 设置每个连接中使用的最大活动会话数
    private int maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION;
    public final static int DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION = 300;
    // 线程池数量
    private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
    public final static int DEFAULT_THREAD_POOL_SIZE = 10; //50
    // 强制使用同步返回数据的格式
    private boolean useAsyncSendForJMS = DEFAULT_USE_ASYNC_SEND_FOR_JMS;
    public final static boolean DEFAULT_USE_ASYNC_SEND_FOR_JMS = true;
    // 是否持久化消息
    private boolean isPersistent = DEFAULT_IS_PERSISTENT;
    public final static boolean DEFAULT_IS_PERSISTENT = true;

    // 连接地址
    private String brokerUrl;

    private String userName;

    private String password;

    private ExecutorService threadPool;

    private PooledConnectionFactory pooledConnectionFactory;

    /*private Connection connection;

    private MessageProducer producer;

    private Session session;*/

    /*private static MqProceducer unique = new MqProceducer();

    private MqProceducer() {
    }

    public static MqProceducer getInstance() {
        return unique;
    }*/

    public MqProceducer(String brokerUrl, String userName, String password) {
        this(brokerUrl, userName, password, DEFAULT_MAX_CONNECTIONS,
                DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION,
                DEFAULT_THREAD_POOL_SIZE, DEFAULT_USE_ASYNC_SEND_FOR_JMS,
                DEFAULT_IS_PERSISTENT);
    }

    public MqProceducer(String brokerUrl, String userName, String password,
                        int maxConnections, int maximumActiveSessionPerConnection,
                        int threadPoolSize, boolean useAsyncSendForJMS, boolean isPersistent) {
        this.useAsyncSendForJMS = useAsyncSendForJMS;
        this.isPersistent = isPersistent;
        this.brokerUrl = brokerUrl;
        this.userName = userName;
        this.password = password;
        this.maxConnections = maxConnections;
        this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
        this.threadPoolSize = threadPoolSize;
        init();
    }

    private void init() {
        // 设置JAVA线程池
        this.threadPool = Executors.newFixedThreadPool(this.threadPoolSize);
        // ActiveMQ的连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.userName, this.password, this.brokerUrl);
        connectionFactory.setUseAsyncSend(this.useAsyncSendForJMS);
        // Active中的连接池工厂
        this.pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
        this.pooledConnectionFactory.setCreateConnectionOnStartup(true);
        this.pooledConnectionFactory.setMaxConnections(this.maxConnections);
        this.pooledConnectionFactory.setMaximumActiveSessionPerConnection(this.maximumActiveSessionPerConnection);
    }

    private MqProceducerHelper connectionTopic(String queue, String textMessage) throws JMSException {
        return createHelper(queue, textMessage, (s, q) -> s.createProducer(s.createTopic(q)));
    }

    private MqProceducerHelper connectionQueue(String queue, String textMessage) throws JMSException {
        return createHelper(queue, textMessage, (s, q) -> s.createProducer(s.createQueue(q)));
    }

    private MqProceducerHelper createHelper(String queue, String textMessage, MessageProducerFunction function)
            throws JMSException {
        // 从连接池工厂中获取一个连接
        Connection connection = this.pooledConnectionFactory.createConnection();
        connection.start();
        // false 参数表示 为非事务型消息，后面的参数表示消息的确认类型
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = function.apply(session, queue);
        // set delevery mode不设置，默认就是持久的
        producer.setDeliveryMode(this.isPersistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
        Message message = null;
        if (textMessage != null) {
            message = session.createTextMessage(textMessage);
        }
        return new MqProceducerHelper(connection, session, producer, message);
    }

    /**
     * 执行发送消息的具体方法
     */
    public void send(final String queue, final String textMessage) {
        if (ConfigConsts.SERVER_INDEPENDENT) {
            // 独立模式，不启用mq
            return;
        }
        // 直接使用线程池来执行具体的调用
       // this.threadPool.execute(new Runnable() {
            //public void run() {
                try {
                    sendMsg(queue, textMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
           // }
        //});
    }

    public void send(final String queue, final List<String> listMessage) {
        if (ConfigConsts.SERVER_INDEPENDENT) {
            // 独立模式，不启用mq
            return;
        }
        //this.threadPool.execute(new Runnable() {
            //public void run() {
                try {
                    sendMsgList(queue, listMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //}
       // });
    }

    /**
     * 真正的执行消息发送
     */
    private void sendMsg(String queue, String textMessage) throws JMSException {
        // map convert to javax message
        this.connectionQueue(queue, textMessage).send();
    }

    private void sendMsgList(String queue, List<String> listMessage) throws JMSException {
        // map convert to javax message
        for (String textMessage : listMessage) {
            this.sendMsg(queue, textMessage);
        }
    }

    /**
     * yongfan
     * 2016.8.30
     * 发送文件
     *
     * @param path         项目根目录
     * @param fileOrFolder filename为空时，这是文件路径，否则是文件所在文件夹的路径（不包含"${path}/"）
     */
    public void sendFileMsg(String queue, String filename, String path, String fileOrFolder) throws JMSException {
        MqProceducerHelper helper = this.connectionQueue(queue, null);
        // map convert to javax message
        BytesMessage message = helper.getSession().createBytesMessage();
        if (filename == null) {
            message.writeBytes(getImageFromFile(path + File.separator + fileOrFolder));
            message.setStringProperty("fileName", "");
        } else {
            message.writeBytes(getImageFromFile(path + File.separator + fileOrFolder + File.separator + filename));
            message.setStringProperty("fileName", filename);
        }
        message.setStringProperty("url", fileOrFolder);
        helper.send(message);
    }

    /**
     * 发送zip文件，内网接收后需要解压
     *
     * @param path     项目根目录
     * @param folder   文件所在文件夹的路径（不包含"${path}/"），并且也是解压路径
     * @param fileName 文件名
     */
    public void sendZipMsg(String queue, String path, String folder, String fileName) throws JMSException {
        MqProceducerHelper helper = this.connectionQueue(queue, null);
        BytesMessage message = helper.getSession().createBytesMessage();
        message.writeBytes(getImageFromFile(path + File.separator + folder + File.separator + fileName));
        message.setStringProperty("url", folder);
        message.setStringProperty("fileName", fileName);
        message.setStringProperty("type", "zip");
        helper.send(message);
    }

    public static void saveFile(String strUrl, String fileName) {
        byte[] btImg = getImageFromNetByUrl(strUrl);
        if (btImg == null) {
            return;
        }
        //存到公网
        String imgFullPath = UploadUtil.UPLOAD_USER_HEADER_PATH;
        File file = new File(imgFullPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(imgFullPath + File.separator + fileName + ".jpg");
        //file = new File("c://"+fileName+".jpg");
        FileOutputStream fops;
        try {
            fops = new FileOutputStream(file);
            fops.write(btImg);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFileMsg(String queue, String filename, String url) throws JMSException {
        MqProceducerHelper helper = this.connectionQueue(queue, null);
        // map convert to javax message
        BytesMessage message = helper.getSession().createBytesMessage();
        //String url="http://221.237.156.219:9997/download/goods_image/20160704_171012_260210.jpg";
        message.writeBytes(getImageFromNetByUrl(url));
        message.setStringProperty("fileName", filename + ".jpg");
        message.setStringProperty("url", UploadUtil.USER_HEADER_PATH);
        helper.send(message);
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            //System.out.println("length:"+btImg.length);
            return readInputStream(inStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getImageFromFile(String path) {
        try {
            File file = new File(path);
            FileInputStream inStream = new FileInputStream(file);
            //System.out.println("length:"+btImg.length);
            return readInputStream(inStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public void onException(JMSException e) {
        e.printStackTrace();
    }

    /**
     * 1.transacted事务，事务成功commit,才会将消息发送到mom中
     * 2.acknowledgeMode消息确认机制
     * 1）、带事务的session
     * 如果session带有事务，并且事务成功提交，则消息被自动签收。如果事务回滚，则消息会被再次传送。
     * 2）、不带事务的session
     * 不带事务的session的签收方式，取决于session的配置。
     * Activemq支持一下三種模式：
     * Session.AUTO_ACKNOWLEDGE  消息自动签收
     * Session.CLIENT_ACKNOWLEDGE  客戶端调用acknowledge方法手动签收
     * Session.DUPS_OK_ACKNOWLEDGE 不是必须签收，消息可能会重复发送。在第二次重新传送消息的时候，消息
     * 头的JmsDelivered会被置为true标示当前消息已经传送过一次，客户端需要进行消息的重复处理控制。
     * 代码示例如下：
     * session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
     *
     * @throws JMSException
     */
    public void sendTopic(String queue, String text) throws JMSException {
        this.connectionTopic(queue, text).send();
    }

}
