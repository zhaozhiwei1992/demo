package com.example.config;

import com.rabbitmq.client.RecoveryListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.ErrorHandler;

/**
 * @Title: RabbitMqAMQPDirectExchangeConfig
 * @Package com/example/config/RabbitMqAMQPDirectExchangeConfig.java
 * @Description:
 * 参考<<spring in action4 将xml用java注解方式搞起来>>
 *
 * 注释部分是一大堆扩展点，根据实际情况使用
 *
 * @author zhaozhiwei
 * @date 2022/3/30 下午5:29
 * @version V1.0
 */
@Configuration
public class RabbitMqAMQPDirectExchangeConfig {

    public static final String QUEUE_NAME = "user.alert.queue";

    public static final String TOPIC_NAME = "user.alert.topic";

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Bean
    public ConnectionFactory connectionFactory(
//            ConnectionListener connectionListener,
//            RecoveryListener recoveryListener,
//            ChannelListener channelListener
    ) {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        setAddresses：设置了rabbitmq的地址、端口，集群部署的情况下可填写多个，“,”分隔。
//        connectionFactory.setHost(rabbitmqProperties.getHost());
//        connectionFactory.setPort(Integer.parseInt(rabbitmqProperties.getPort()));
        connectionFactory.setAddresses(rabbitmqProperties.getHost() + ":" + rabbitmqProperties.getPort());
        connectionFactory.setUsername(rabbitmqProperties.getUsername());
        connectionFactory.setPassword(rabbitmqProperties.getPassword());

//        connectionFactory.setVirtualHost("/");

//        设置缓存模式，共有两种，CHANNEL和CONNECTION模式。
//        CHANNEL模式，程序运行期间ConnectionFactory会维护着一个Connection，所有的操作都会使用这个Connection，但一个Connection中可以有多个Channel
//        ，操作rabbitmq之前都必须先获取到一个Channel，否则就会阻塞（可以通过setChannelCheckoutTimeout()
//        设置等待时间），这些Channel会被缓存（缓存的数量可以通过setChannelCacheSize()设置）；
//        CONNECTION模式，这个模式下允许创建多个Connection，会缓存一定数量的Connection，每个Connection中同样会缓存一些Channel，除了可以有多个Connection
//        ，其它都跟CHANNEL模式一样。
//        这里的Connection和Channel是spring-amqp中的概念，并非rabbitmq中的概念，官方文档对Connection和Channel有这样的描述：
//        Sharing of the connection is possible since the "unit of work" for messaging with AMQP is actually a
//        "channel" (in some ways, this is similar to the relationship between a Connection and a Session in JMS).
//        关于CONNECTION模式中，可以存在多个Connection的使用场景，官方文档的描述：
//        The use of separate connections might be useful in some environments, such as consuming from an HA cluster,
//        in conjunction with a load balancer, to connect to different cluster members.

//        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);


//        设置每个Connection中（注意是每个Connection）可以缓存的Channel数量，注意只是缓存的Channel数量，不是Channel的数量上限，操作rabbitmq之前（send/receive
//        message等）要先获取到一个Channel，获取Channel时会先从缓存中找闲置的Channel，如果没有则创建新的Channel，当Channel数量大于缓存数量时，多出来没法放进缓存的会被关闭。
//        注意，改变这个值不会影响已经存在的Connection，只影响之后创建的Connection。

//        connectionFactory.setChannelCacheSize(25);

//        当这个值大于0时，channelCacheSize不仅是缓存数量，同时也会变成数量上限，从缓存获取不到可用的Channel时，不会创建新的Channel
//        ，会等待这个值设置的毫秒数，到时间仍然获取不到可用的Channel会抛出AmqpTimeoutException异常。
//        同时，在CONNECTION模式，这个值也会影响获取Connection的等待时间，超时获取不到Connection也会抛出AmqpTimeoutException异常。
//        connectionFactory.setChannelCheckoutTimeout(0);
//
//        connectionFactory.setPublisherReturns(false);
//
//        connectionFactory.setPublisherConfirms(false);

//        connectionFactory.addConnectionListener(connectionListener);
//
//        connectionFactory.addChannelListener(channelListener);
//
//        connectionFactory.setRecoveryListener(recoveryListener);

        //connectionFactory.setConnectionCacheSize(1);

        //connectionFactory.setConnectionLimit(Integer.MAX_VALUE);

        return connectionFactory;
    }

    /**
     * 默认会有
     * 一个没有名称的direct Exchange，所有的队列都会绑定到这个Exchange
     * 上，并且routing key与队列的名称相同
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    /**
     * @data: 2022/3/30-下午5:12
     * @User: zhaozhiwei
     * @method: rabbitAdmin

     * @return: org.springframework.amqp.rabbit.core.RabbitAdmin
     * @Description: 描述
     * 配置元素要与<admin>元素一起使用。<admin>元素会创建一
     * 个RabbitMQ管理组件（administrative component），它会自动创建
     * （如果它们在RabbitMQ代理中尚未存在的话）上述这些元素所声明
     * 的队列、Exchange以及binding
     *
     * 如果不初始化这个RabbitAdmin, 则不会自动在rabbitmq中创建queue或者exchange
     */
    @Bean
    public RabbitAdmin rabbitAdmin(){
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(true);
        rabbitAdmin.declareQueue(queue());
//        rabbitAdmin.declareExchange();
        return rabbitAdmin;
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory amqpConnectionFactory
//            , RabbitTemplate.ReturnCallback returnCallback,
//                                     RabbitTemplate.ConfirmCallback confirmCallback,
//                                     RetryTemplate retryTemplate,
//                                     MessageConverter messageConverter
    ) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate();

//        setConnectionFactory：设置spring-amqp的ConnectionFactory。
        rabbitTemplate.setConnectionFactory(amqpConnectionFactory);
//        setRetryTemplate：设置重试机制，
//        rabbitTemplate.setRetryTemplate(retryTemplate);

//        设置MessageConverter，用于java对象与Message对象（实际发送和接收的消息对象）之间的相互转换
//        rabbitTemplate.setMessageConverter(messageConverter);

//        setChannelTransacted：打开或关闭Channel的事务
        rabbitTemplate.setChannelTransacted(false);
//        rabbitTemplate.setReturnCallback(returnCallback);
//        rabbitTemplate.setConfirmCallback(confirmCallback);
//        setMandatory：设为true使ReturnCallback生效
        rabbitTemplate.setMandatory(false);
        rabbitTemplate.setDefaultReceiveQueue(QUEUE_NAME);
//        rabbitTemplate.setRoutingKey(queue().getName());
//        rabbitTemplate.setExchange(QUEUE_NAME + ".exchange");

        return rabbitTemplate;

    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            CachingConnectionFactory cachingConnectionFactory
//            , ErrorHandler errorHandler,
//            MessageConverter messageConverter
    ) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        setConnectionFactory：设置spring-amqp的ConnectionFactory。
        factory.setConnectionFactory(cachingConnectionFactory);
//        setMessageConverter：对于consumer端，MessageConverter也可以在这里配置。
//        factory.setMessageConverter(messageConverter);
//        setAcknowledgeMode：设置consumer端的应答模式，共有三种：NONE、AUTO、MANUAL。
//        NONE，无应答，这种模式下rabbitmq默认consumer能正确处理所有发出的消息，所以不管消息有没有被consumer收到，有没有正确处理都不会恢复；
//        AUTO，由Container自动应答，正确处理发出ack信息，处理失败发出nack信息，rabbitmq发出消息后将会等待consumer端的应答，只有收到ack确认信息才会把消息清除掉，收到nack
//        信息的处理办法由setDefaultRequeueRejected()方法设置，所以在这种模式下，发生错误的消息是可以恢复的。
//        MANUAL，基本同AUTO模式，区别是需要人为调用方法给应答。

//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);

//        setConcurrentConsumers：设置每个MessageListenerContainer将会创建的Consumer的最小数量，默认是1个。
        factory.setConcurrentConsumers(1);
//        setMaxConcurrentConsumers：设置每个MessageListenerContainer将会创建的Consumer的最大数量，默认等于最小数量。
        factory.setMaxConcurrentConsumers(1);
//        setPrefetchCount：设置每次请求发送给每个Consumer的消息数量。
        factory.setPrefetchCount(250);
//        setChannelTransacted：设置Channel的事务。

//        factory.setChannelTransacted(false);

//        setTxSize：设置事务当中可以处理的消息数量。

//        factory.setTxSize(1);

//        setDefaultRequeueRejected：设置当rabbitmq收到nack/reject确认信息时的处理方式，设为true，扔回queue头部，设为false，丢弃。

//        factory.setDefaultRequeueRejected(true);

//        setErrorHandler：实现ErrorHandler接口设置进去，所有未catch的异常都会由ErrorHandler处理。
//        factory.setErrorHandler(errorHandler);
        return factory;
    }

}