package com.haitai.haitaitv.component.jms;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.component.util.encryption.DES3Utils;

import org.apache.activemq.BlobMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.TextMessage;


public class JmsEventHandler {
    private Logger log = LogManager.getLogger(JmsEventHandler.class);

    public void eventHandler(Message message) {
        if (message instanceof BytesMessage) {
            fileEventHandler(message);
        } else if (message instanceof TextMessage) {
            messageEventHandler(message);
        } else if (message instanceof BlobMessage) {
            blobMessageHandler(message);
        }
    }

    private String getData(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            return DES3Utils.INSTANCE.decryptString(textMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void messageEventHandler(Message message) {
        String data = getData(message);
        log.info("消费者的解密串：" + data);
//        DbEventDTO events = JSONObject.parseObject(data, DbEventDTO.class);
//        if (events == null) {
//            log.error("***ActiveMq Consumer: Message event -> Empty message body***");
//            return;
//        }
//        String queueId = events.getQueueId();
//        if (events.getTables() == null || events.getTables().size() == 0) {
//            log.info("***ActiveMq Consumer: Message event -> Empty tables***");
//            return;
//        }
//
//        //遍历所有数据更新行为
//        for (DbTablesDTO event : events.getTables()) {
//            for (MqEventCenter table : MqEventCenter.values()) {
//                if (table.name().equalsIgnoreCase(event.getTable())) {
//                    table.textEvent(event, queueId);
//                }
//            }
//        }
    }

    private void fileEventHandler(Message message) {
        if (!(message instanceof BytesMessage)) {
            log.error("***ActiveMq Consumer: File event -> Error message body***");
            return;
        }
        BytesMessage bytesMessage = (BytesMessage) message;
        try {
//            String tableName = bytesMessage.getStringProperty("tableName");
//            tableName = DES3Utils.INSTANCE.decryptString(tableName);
//            if (StringUtils.isBlank(tableName)) {
//                log.error("***ActiveMq Consumer: File event -> Empty tableName***");
//                return;
//            }
//
//            String row = DES3Utils.INSTANCE.decryptString(bytesMessage.getStringProperty("row"));
//            String content = DES3Utils.INSTANCE.decryptString(bytesMessage.getStringProperty("content"));
//            log.info("***ActiveMq FileMessage****");
//            log.info("tableName=" + tableName + ", row=" + row + ", content=" + content);
//
//            //遍历所有行为
//            for (MqEventCenter table : MqEventCenter.values()) {
//                if (table.name().equalsIgnoreCase(tableName)) {
//                    table.fileEvent(bytesMessage);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blobMessageHandler(Message message) {
        BlobMessage blobMessage = (BlobMessage) message;
        System.out.println("blob message receive is not available yet");
    }

}
