package com.example.service;

import com.example.config.RabbitMqAMQPDirectExchangeConfig;
import com.example.domain.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "localAlertService")
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public void sendUserAlert(User user) {

//        使用消息转换 (推荐)
        rabbitTemplate.convertAndSend(RabbitMqAMQPDirectExchangeConfig.QUEUE_NAME, user);
    }

    @Override
    public User receiveUserAlert() {
        final Object o = rabbitTemplate.receiveAndConvert();
        return (User) o;
    }
}
