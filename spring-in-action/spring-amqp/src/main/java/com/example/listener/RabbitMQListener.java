package com.example.listener;

import com.example.config.RabbitMqAMQPDirectExchangeConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = RabbitMqAMQPDirectExchangeConfig.QUEUE_NAME)
    public void listen(Message message) {

//        JSON.parseObject(new String(message.getBody()), typeReference);
        System.out.println(message);

    }

}
