package com.haitai.haitaitv.component.jms;



import javax.jms.*;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;

import com.haitai.haitaitv.component.util.FileUtil;

import java.io.*;
import java.net.URL;

/**
 * @author xuxuh
 */
public class JmsProducer extends BaseHelper implements ExceptionListener {
    private final String FILENAME = "fileName";
    private final String TABLE_NAME = "tableName";
    private final String ROW = "row";
    private final String CONTENT = "content";

    public JmsProducer() {
        super();
    }

    /**
     * 发送文本消息
     *
     * @throws JMSException
     */
    public void sendTextMsg(final String queue, final String textMessage) throws JMSException {
        JmsProductHelper helper = super.connectionQueue(queue);
        Message message = helper.getSession().createTextMessage(textMessage);
        helper.send(message);
    }

    /**
     * 发送文件
     *
     * @param queue     队列名
     * @param tableName 表名
     * @param row       需要更新的字段
     * @param content   附带内容，一般是json格式的实体
     * @param file      文件
     */
    public void sendFileMsg(String queue, String tableName, String row, String content, byte[] file, String fileName) throws Exception {
        JmsProductHelper helper = super.connectionQueue(queue);
        // map convert to javax message
        BytesMessage message = helper.getSession().createBytesMessage();
        if (file != null) {
            message.writeBytes(file);
            message.setStringProperty(FILENAME, fileName);
        }
        message.setStringProperty(TABLE_NAME, tableName);
        message.setStringProperty(ROW, row);
        message.setStringProperty(CONTENT, content);
        helper.send(message);
    }

    /**
     * 发送空文件文件消息
     *
     * @param queue     队列名
     * @param tableName 表名
     * @param row       需要更新的字段
     * @param content   附带内容，一般是json格式的实体
     * @param fileName  文件名
     */
    public void sendFileMsg(String queue, String tableName, String row, String content, String fileName) throws Exception {
        JmsProductHelper helper = super.connectionQueue(queue);
        BytesMessage message = helper.getSession().createBytesMessage();
        message.setStringProperty(FILENAME, fileName);
        message.setStringProperty(TABLE_NAME, tableName);
        message.setStringProperty(ROW, row);
        message.setStringProperty(CONTENT, content);
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
        JmsProductHelper helper = super.connectionQueue(queue);
        BytesMessage message = helper.getSession().createBytesMessage();
       // message.writeBytes(FileUtil.getBytesFromFile(path + File.separator + folder + File.separator + fileName));
        message.setStringProperty("url", folder);
        message.setStringProperty(FILENAME, fileName);
        message.setStringProperty("type", "zip");
        helper.send(message);
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
    public void sendTopic(String queue, String msg) throws JMSException {
        JmsProductHelper helper = super.connectionTopic(queue);
        Message message = helper.getSession().createTextMessage(msg);
        helper.send(message);
    }

    @Override
    public void onException(JMSException e) {
        e.printStackTrace();
    }

    /**
     * 以file方式发送blob文件消息
     *
     * @throws JMSException
     */
    public void sendBlobMsg(final String queue, String tableName, String row, String content, File file, String fileName) throws JMSException {
        JmsProductHelper helper = super.connectionBlob(queue);
        ActiveMQSession blobSession = helper.getMqSession();
        BlobMessage message = blobSession.createBlobMessage(file);
        sendBlobMsg(helper, message, tableName, row, content, fileName);
    }

    /**
     * 以url方式发送blob文件消息
     *
     * @throws JMSException
     */
    public void sendBlobMsg(final String queue, String tableName, String row, String content, URL url, String fileName) throws JMSException {
        JmsProductHelper helper = super.connectionBlob(queue);
        ActiveMQSession blobSession = helper.getMqSession();
        BlobMessage message = blobSession.createBlobMessage(url);
        sendBlobMsg(helper, message, tableName, row, content, fileName);
    }

    /**
     * 以inputStream方式发送blob文件消息
     *
     * @throws JMSException
     */
    public void sendBlobMsg(final String queue, String tableName, String row, String content, InputStream in, String fileName) throws JMSException {
        JmsProductHelper helper = super.connectionBlob(queue);
        ActiveMQSession blobSession = helper.getMqSession();
        BlobMessage message = blobSession.createBlobMessage(in);
        sendBlobMsg(helper, message, tableName, row, content, fileName);
    }

    private void sendBlobMsg(JmsProductHelper helper, BlobMessage message, String tableName, String row, String content, String fileName) throws JMSException {
        message.setStringProperty(FILENAME, fileName);
        message.setStringProperty(TABLE_NAME, tableName);
        message.setStringProperty(CONTENT, content);
        message.setStringProperty(ROW, row);
        helper.send(message);
    }
}
