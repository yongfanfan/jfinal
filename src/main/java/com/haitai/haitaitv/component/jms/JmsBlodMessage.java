package com.haitai.haitaitv.component.jms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.jms.JMSException;
import javax.jms.Message;




import org.apache.activemq.BlobMessage;













import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.poi.ss.usermodel.DateUtil;

import com.haitai.haitaitv.component.constant.JmsConsts;


public class JmsBlodMessage {
    
    public static void main(String[] args) throws Exception {
       customer();
       //producer();
    }
    
    public static void producer() throws JMSException{
        File file = new File("E://ppt.rar");
        System.out.println("size:" + file.length());
       // System.out.println("***********send time:" + DateUtil.format());
    }
    
    public static void customer() throws Exception{
        JmsConsumer p = JmsConsumer.of(JmsConsts.JMAKE_TO_PRIVATE_DBTABLE,"aaa");
        p.setMessageListener(message -> getmsg(message));
        p.start();
    }
    
    //filename = msg.getJMSMessageID().toString().replace(":", "_")
    public static void getmsg(Message message){
        BlobMessage blobMessage = (BlobMessage) message;
        try {
            //System.out.println("***********receive time:" + DateUtil.format());
            String fileName = blobMessage.getStringProperty("filename");
            long size = blobMessage.getLongProperty("size");
            URL url = blobMessage.getURL();
            System.out.println(url);
            InputStream inputStream = blobMessage.getInputStream();
            BufferedInputStream bin = new BufferedInputStream(inputStream); 
            FileOutputStream out = new FileOutputStream(new File("D://" + fileName));
            byte[] buffer = new byte[4096];
            int len ;
            while ((len = bin.read(buffer)) != -1) {
               out.write(buffer, 0, len);
            }
            inputStream.close();
            bin.close();
            out.close();
            out.flush();
           // System.out.println("***********update time:" + DateUtil.format());
            //注意处理完后需要手工删除服务器端文件    
            ((ActiveMQBlobMessage)blobMessage).deleteFile();
            System.out.println("完成文件接收：" + fileName + ",size: "+ size);
        } catch (Exception e) {
            e.printStackTrace();
            //这里可以存入一条未成功记录，然后等晚上定时器发回公网，自己写了
        }
    }
    
    
}
