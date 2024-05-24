package com.lx.demo.springbootrabbitmq.simple;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {

    private static final String EXCHANGE_NAME = "exchange demo";
    private static final String ROUTING_KEY = "routingkey demo";
    private static final String QUEUE_NAME = "queue demo";


    public static void main(String[] args) throws IOException, TimeoutException {
//        测试里的hello world
//        test1();

//        测试死信队列， 创建一个消息并让其变为死信
        test2();
    }

    private static void test2() throws IOException, TimeoutException {
        final Connection connection = getConnection();
        Channel channel = connection.createChannel();
        final Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", "dead_exchange");
        args.put("x-dead-letter-routing-key", "dead_routingkey");
        // 设置死信队列
        channel.exchangeDeclare("dead_exchange", BuiltinExchangeType.DIRECT, true);
        channel.exchangeDeclare("normal_exchange", BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare("normal_queue", true, false, false, args);
        channel.queueBind("normal_queue", "normal_exchange", "normal_routingkey");
        channel.queueDeclare("dead_queue", true, false, false, null);
        channel.queueBind("dead_queue", "dead_exchange", "dead_routingkey");
        // 设置消息10秒超时,客户端未接收进入死信
        channel.basicPublish("normal_exchange", "normal_routingkey", null, "hello world".getBytes());

        // 等待10s,可以查看控制台页面,队列dead_queue.Ready有值
    }

    private static void test1() throws IOException, TimeoutException {
        final Connection connection = getConnection();
        Channel channel = connection.createChannel();// 创建信道

// 创建一个 type="direct" 、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, null);
//创建一个持久 化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//将交换器与队列通过路由键绑定,  这里的 RoutingKey 和 BindingKey 是同 一 个东西。在 direct 交换器类型下，
//RoutingKey 和 BindingKey 需要完全匹配才能使用
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY + "_2");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY + "_3");
//发送一条持久化的消息: hello world !
        String message = "Hello World !";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY + "_2", MessageProperties.PERSISTENT_TEXT_PLAIN, (message + "_2").getBytes());
//关闭资源

        connection.addShutdownListener(cause -> {
            System.out.println("connection closed");
        });
        channel.close();
        connection.close();
    }

    private static Connection getConnection() throws IOException, TimeoutException {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yaml"));
        Map<String, Object> map = yamlMapFactoryBean.getObject();
        map = (Map) ((Map) map.get("spring")).get("rabbitmq");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(String.valueOf(map.get("host")));
        factory.setPort(Integer.parseInt(String.valueOf(map.get("port"))));
        factory.setUsername(String.valueOf(map.get("username")));
        factory.setPassword(String.valueOf(map.get("password")));
        Connection connection = factory.newConnection();// 创建连接
        return connection;
    }
}
