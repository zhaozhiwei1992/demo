package com.lx.demo.springbootrabbitmq.simple;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {

    private static final String QUEUE_NAME = "queue demo";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 测试消费者, 跟producer中test1对应
        test1();

    }

    private static void test1() throws IOException, TimeoutException {
        final Connection connection = getConnection();
        final Channel channel = connection.createChannel(); // 创建信道
        channel.basicQos(64); // 设置客户端最多接收未被 ack 的消息的个数
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" recv message: " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    channel.basicAck(envelope.getDeliveryTag(), false);

                }

            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);

//等待回调函数执行完毕之后 ， 关闭资源

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (
                InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        channel.close();
        connection.close();
    }

    private static Connection getConnection() throws IOException, TimeoutException {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yaml"));
        Map<String, Object> map = yamlMapFactoryBean.getObject();
        map = (Map) ((Map) map.get("spring")).get("rabbitmq");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(String.valueOf(map.get("username")));
        factory.setPassword(String.valueOf(map.get("password")));

//这里的连接方式与生产者的 demo 略有不同 ， 注意辨别区别
        Connection connection = factory.newConnection(String.valueOf(map.get("host"))); // 创建连接
        return connection;
    }
}