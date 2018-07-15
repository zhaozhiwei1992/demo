package com.lx.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 赵志伟
 * @ClassName: JMSQueueProducer
 * @description [描述该类的功能]
 * @create 2018-07-08 下午3:58
 **/
public class JMSQueueProducer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = null;
        try {
            //建立连接
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建队列, mq服务不出问题，这个队列创建好后就一只存在
            Destination myQueue = session.createQueue("myQueue");

            //创建发送者
            MessageProducer messageProducer = session.createProducer(myQueue);

            //创建消息
            TextMessage textMessage = session.createTextMessage("hello world");
            messageProducer.send(textMessage);

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
