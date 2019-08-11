package com.lx.demo.springbootrabbitmq.fanoutexchange.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "fanout.1")
public class FanoutConsumer1 {

    @RabbitHandler
    public void receiveMessageString(String msg){
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
    }
}
