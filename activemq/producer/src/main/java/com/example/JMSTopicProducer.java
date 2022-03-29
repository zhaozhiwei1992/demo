package com.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 赵志伟
 * @ClassName: JMSQueueProducer
 * @description [描述该类的功能]
 * @create 2018-07-08 下午3:58
 **/
public class JMSTopicProducer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.7.6:61616");
        Connection connection = null;
        try {
            //建立连接
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建主题
            Destination mytopic = session.createTopic("mytopic");

            //创建发送者
            MessageProducer messageProducer = session.createProducer(mytopic);

            //创建消息
            TextMessage textMessage = session.createTextMessage("刘亦菲发微博了。。。。");
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
