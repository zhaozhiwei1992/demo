package com.example.config;

import com.example.domain.User;
import com.example.service.CustomUserAlertHandler;
import com.example.service.UserAlertHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageMethodArgumentResolver;

import javax.jms.ConnectionFactory;
import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
public class ActiveMqJMSConfig {

    @Bean
    public ActiveMQQueue activeMQQueue() {
        final ActiveMQQueue activeMQQueue = new ActiveMQQueue();
        activeMQQueue.setPhysicalName("user.alert.queue");
        return activeMQQueue;
    }

    @Bean
    public ActiveMQTopic activeMQTopic() {
        final ActiveMQTopic activeMQTopic = new ActiveMQTopic();
        activeMQTopic.setPhysicalName("user.alert.topic");
        return activeMQTopic;
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory
                .setConnectionFactory(connectionFactory());
        factory.setConcurrency("3-10");

        return factory;
    }

    @Bean
    public SimpleJmsListenerContainerFactory simpleJmsListenerContainerFactory() {
        SimpleJmsListenerContainerFactory factory =
                new SimpleJmsListenerContainerFactory();
        factory
                .setConnectionFactory(connectionFactory());

        return factory;
    }

    @Bean
    public CustomUserAlertHandler customUserAlertHandler() {
//        以POJO的方式处理消息
        return new CustomUserAlertHandler();
    }

    @Bean
    public UserAlertHandler userAlertHandler() {
//        以POJO的方式处理消息
        return new UserAlertHandler("1");
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(CustomUserAlertHandler customUserAlertHandler) throws NoSuchMethodException {

//        TODO 未跑通
//        <<spring-in-action4>>  <jms:container .../>
//        final MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
//        endpoint.setDestination(activeMQQueue().getPhysicalName());
//        final Method method = customUserAlertHandler.getClass().getMethod("handlerUserAlert", User.class);
//        endpoint.setMethod(method);
//        endpoint.setBean(customUserAlertHandler);
//
//        final DefaultMessageHandlerMethodFactory handlerMethodFactory =
//                new DefaultMessageHandlerMethodFactory();
//        final MessageMethodArgumentResolver methodArgumentResolver = new MessageMethodArgumentResolver();
//        methodArgumentResolver.resolveArgument(new MethodParameter(method, 0))
//        handlerMethodFactory.setArgumentResolvers(Arrays.asList(methodArgumentResolver));
//        endpoint.setMessageHandlerMethodFactory(handlerMethodFactory);

        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setMessageListener(userAlertHandler());
        endpoint.setDestination(activeMQQueue().getPhysicalName());

        return defaultJmsListenerContainerFactory().createListenerContainer(endpoint);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

//                指定目的地, 可以是队列，也可以是一个主题，这取决于应用的需求
//                这里只是个字符串, 默认是队列
//        com.example.service.AlertServiceImpl.sendUserAlert
//        jmsTemplate.setDefaultDestinationName("user.alert.queue");
//        jmsTemplate.setDefaultDestination(activeMQTopic());
        jmsTemplate.setDefaultDestination(activeMQQueue());

//        设置json消息转换,
//        TODO 接收时空指针，暂未处理
//        参考: https://stackoverflow.com/questions/32385062/spring-mappingjackson2messageconverter-give-null-pointer-exception
//        jmsTemplate.setMessageConverter(messageConverter());

        return jmsTemplate;
    }

    @Value("${spring.activemq.brokerUrl}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//         Please take a look at http://activemq.apache.org/objectmessage.html for more information on how to
//         configure trusted classes.
        activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.example.domain"));
        return activeMQConnectionFactory;
    }
}