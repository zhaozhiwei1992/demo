package com.lx.demo.springbootrabbitmq.fanoutexchange.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FanoutExchangeProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * fanout的方式不再需要routing-key
     * @param msg
     */
    public void sendMsg(String msg){
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
        // 这里一定要注意函数重载，只有三个参数时，第一个才是exchange
        rabbitTemplate.convertAndSend("fanoutExchange","", msg);
    }
}
