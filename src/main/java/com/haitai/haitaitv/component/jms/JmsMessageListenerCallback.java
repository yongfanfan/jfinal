package com.haitai.haitaitv.component.jms;


import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.concurrent.*;

/**
 * 消息消费者中使用的多线程消息监听服务
 */
public class JmsMessageListenerCallback implements MessageListener {
    // 提供消息回调调用接口
    private JmsMessageCallback messageCallback;

    private ExecutorService handleThreadPool;

    /**
     * 支持阻塞的固定大小的线程池不传线程数就是单线程
     * @param messageCallback 回调
     */
    public JmsMessageListenerCallback(JmsMessageCallback messageCallback) {
        this(10, messageCallback);
    }

    /**
     * 支持阻塞的固定大小的线程池不传线程数就是单线程
     * @param maxHandleThreads 线程数
     * @param messageCallback 回调
     */
    public JmsMessageListenerCallback(int maxHandleThreads, JmsMessageCallback messageCallback) {
        this.messageCallback = messageCallback;
        // 支持阻塞的固定大小的线程池(自行手动创建的)
        this.handleThreadPool = new JmsThreadPoolExecutor(maxHandleThreads);
    }

    /**
     * 监听程序中自动调用的方法
     */
    @Override
    public void onMessage(final Message message) {
        // 使用支持阻塞的固定大小的线程池来执行操作
        this.handleThreadPool.execute(() -> messageCallback.apply(message));
    }

}
