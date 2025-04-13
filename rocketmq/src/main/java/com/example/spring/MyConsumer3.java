package com.example.spring;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Title: MyConsumer3
 * @Package com/example/spring/MyConsumer3.java
 * @Description: 注意这里是另一个消费者组TestGroup2, 不同消费者组可以消费一个queue
 * @author zhaozhiwei
 * @date 2025/4/13 21:54
 * @version V1.0
 */
@Component
@RocketMQMessageListener(topic = "TestTopic", consumerGroup = "TestGroup2")
public class MyConsumer3 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理消息的逻辑
        System.out.println("MyConsumer3 Received message: " + message);
    }
}