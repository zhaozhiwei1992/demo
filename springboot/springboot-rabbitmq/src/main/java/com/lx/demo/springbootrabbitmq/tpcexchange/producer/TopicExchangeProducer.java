package com.lx.demo.springbootrabbitmq.tpcexchange.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class TopicExchangeProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 同时匹配 topic.message和 topic.# 所以会发到这两个queue里
     * @param msg
     */
    public void sendMessage(String msg){
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        log.info("{}, {}", element.getMethodName(), msg);
        rabbitTemplate.convertAndSend("topicExchange", "topic.message"
                , String.join(",", Arrays.asList(element.getClassName(), element.getMethodName(), msg)));
    }

    /**
     * 只能匹配到topic.#, 多了个s, 所以只会匹配发送到 queuemessages
     * BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");
     * 相应的接收也只会是topic.messages
     * @param msg
     */
    public void sendMessages(String msg){
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        log.info("{}, {}", element.getMethodName(), msg);
        rabbitTemplate.convertAndSend("topicExchange", "topic.messages"
                , String.join(",", Arrays.asList(element.getClassName(), element.getMethodName(), msg)));
    }
}
