package com.example.config;

import com.example.service.AlertService;
import com.example.service.AlertServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import javax.jms.ConnectionFactory;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JMXRpcConfiguration
 * @Package com/example/config/JMXRpcConfiguration.java
 * @Description: 使用基于消息的RPC
 * {@see com.example.config.HttpInvokerRpcConfiguration}
 * @date 2022/3/30 上午10:01
 */
@Configuration
public class JMSRpcConfiguration {

    /**
     * @data: 2022/3/30-上午10:08
     * @User: zhaozhiwei
     * @method: userServiceExporter
     * @return: org.springframework.jms.remoting.JmsInvokerServiceExporter
     * @Description: 描述
     * JmsInvokerServiceExporter可以充当JMS监听器。因此，我
     * 们使用<jms:listenercontainer>元素配置它
     *
     * 通过下述客户端发出额消息需要该exporter解析, 毕竟是代理还是有点不一样
     */
    @Bean
    public JmsInvokerServiceExporter jmsServiceExporter() {
        final JmsInvokerServiceExporter jmsInvokerServiceExporter = new JmsInvokerServiceExporter();
        jmsInvokerServiceExporter.setService(new AlertServiceImpl());
        jmsInvokerServiceExporter.setServiceInterface(AlertService.class);
        return jmsInvokerServiceExporter;
    }

    /**
     * @data: 2022/3/30-上午10:09
     * @User: zhaozhiwei
     * @method: jmsClient
     * @return: org.springframework.jms.remoting.JmsInvokerProxyFactoryBean
     * @Description: 描述
     * 配置参考<<spring in action4>>中xml配置
     * 客户端，JmsInvokerProxyFactoryBean用来访问服务
     * spring提供的几个export套路都一样, ProxyFactoryBean都是为了给客户端创建一个访问代理，这里就是创建一个JmsInvokerProxy
     *
     * 场景: 对外提供一个写入到queue的服务，统一管理，避免第三方自己直接访问mq
     */
    @Bean
    public JmsInvokerProxyFactoryBean jmsClient(ConnectionFactory connectionFactory) {
        final JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean = new JmsInvokerProxyFactoryBean();
        jmsInvokerProxyFactoryBean.setServiceInterface(AlertService.class);
        jmsInvokerProxyFactoryBean.setQueueName(ActiveMqJMSConfig.QUEUE_NAME);
        jmsInvokerProxyFactoryBean.setConnectionFactory(connectionFactory);
        return jmsInvokerProxyFactoryBean;
    }

//    @Bean
//    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) throws NoSuchMethodException {
//
//        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//        endpoint.setMessageListener(jmsServiceExporter());
//        endpoint.setDestination(ActiveMqJMSConfig.QUEUE_NAME);
//
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setConcurrency("3-10");
//        return factory.createListenerContainer(endpoint);
//    }

}
