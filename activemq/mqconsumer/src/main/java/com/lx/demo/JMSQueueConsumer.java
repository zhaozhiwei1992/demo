package com.lx.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 赵志伟
 * @ClassName: JMSQueueConsumer
 * @description [描述该类的功能]
 * @create 2018-07-08 下午4:04
 **/
public class JMSQueueConsumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.248:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            //一定要启动连接
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建queue, 这个类似于一个编号
            Destination myqueue = session.createQueue("myQueue");

            //创建消费者
            MessageConsumer consumer = session.createConsumer(myqueue);

            //接收消息, receive 阻塞
            TextMessage textMessage = (TextMessage)consumer.receive();
            System.out.println(textMessage.getText());

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
