package com.lx.demo.springbootrabbitmq.tpcexchange.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听topic.message
 */
@Slf4j
@Component
@RabbitListener(queues = "topic.message")
public class TopicConsumerMessage {

    @RabbitHandler
    public void receiveMsgString(String msg){
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        log.info("{}, {}", element.getMethodName(), msg);
    }
}
