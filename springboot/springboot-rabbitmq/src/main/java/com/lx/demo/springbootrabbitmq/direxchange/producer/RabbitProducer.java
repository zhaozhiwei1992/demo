package com.lx.demo.springbootrabbitmq.direxchange.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsgString(){
        String context = "ttang say: " + new Date();
        log.info(context);
        rabbitTemplate.convertAndSend("ttang", context);
    }

    public void sendMsgInt(int i){
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), i);
        rabbitTemplate.convertAndSend("ttang", i);
    }
}
