package com.lx.demo.springbootrabbitmq.direxchange.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 接收消息
 *
 */
@Component
@RabbitListener(queues = "ttang")
@Slf4j
public class RabbitConsumer {

    @RabbitHandler
    public void receiveMsgString(String tt){
        log.info("收到ttang消息, {}", tt);
    }

    /**
     * Caused by: org.springframework.amqp.AmqpException: Ambiguous methods for payload type: class java.lang.String:
     * msg and msg2
     * 1. 不能有两个payload同时匹配, 会根据消息类型自动匹配
     * @param tt
     */
    @RabbitHandler
    public void receiveMsgInt(Integer tt){
        log.info("收到ttang消息2, {}", tt);
    }
}
