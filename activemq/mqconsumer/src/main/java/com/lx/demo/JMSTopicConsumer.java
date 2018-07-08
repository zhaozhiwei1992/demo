package com.lx.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.*;

/**
 * @author 赵志伟
 * @ClassName: JMSQueueConsumer
 * @description [描述该类的功能]
 * @create 2018-07-08 下午4:04
 **/
public class JMSTopicConsumer{
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            JMSTopicConsumer topicConsumer = new JMSTopicConsumer();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    new JMSTopicConsumer().run();
//                }
//            }).start();
//        }

        ExecutorService service = Executors.newFixedThreadPool(10);
        // 线程池创建并执行10个线程
        for (int i = 0; i < 10; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    new JMSTopicConsumer().run();
                }
            });
        }
        // 线程池使用完成，关闭线程池
        service.shutdown();
    }

    public void run() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.248:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            //一定要启动连接
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Destination mytopic = session.createTopic("mytopic");

            //创建消费者
            MessageConsumer consumer = session.createConsumer(mytopic);
            TextMessage message = (TextMessage)consumer.receive();
            System.out.println("消费者 [" + Thread.currentThread().getId() + "] : " + message.getText());

            session.commit();
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
