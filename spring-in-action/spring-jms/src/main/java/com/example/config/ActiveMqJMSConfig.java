package com.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

@Configuration
public class ActiveMqJMSConfig {

    @Bean
    public ActiveMQQueue activeMQQueue(){
        final ActiveMQQueue activeMQQueue = new ActiveMQQueue();
        activeMQQueue.setPhysicalName("user.alert.queue");
        return activeMQQueue;
    }

    @Bean
    public ActiveMQTopic activeMQTopic(){
        final ActiveMQTopic activeMQTopic = new ActiveMQTopic();
        activeMQTopic.setPhysicalName("user.alert.topic");
        return activeMQTopic;
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter(){
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

//                指定目的地, 可以是队列，也可以是一个主题，这取决于应用的需求
//                这里只是个字符串, 默认是队列
//        com.example.service.AlertServiceImpl.sendUserAlert
//        jmsTemplate.setDefaultDestinationName("user.alert.queue");
//        jmsTemplate.setDefaultDestination(activeMQTopic());
        jmsTemplate.setDefaultDestination(activeMQQueue());

//        设置json消息转换,
//        TODO 接收时空指针，暂未处理
//        jmsTemplate.setMessageConverter(messageConverter());

        return jmsTemplate;
    }

    @Value("${spring.activemq.brokerUrl}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory(){
        final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//         Please take a look at http://activemq.apache.org/objectmessage.html for more information on how to configure trusted classes.
        activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.example.domain"));
        return activeMQConnectionFactory;
    }
}