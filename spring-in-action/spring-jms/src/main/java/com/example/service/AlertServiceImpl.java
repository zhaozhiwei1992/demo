package com.example.service;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Service;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private JmsOperations jmsOperations;

    @Override
    public void sendUserAlert(User user) {

//        发送消息
//        jmsOperations.send(
//                指定目的地, 可以是队列，也可以是一个主题，这取决于应用的需求
//                这里只是个字符串, 默认是队列
//                "user.alert.queue"
//                创建消息
//                , session -> session.createObjectMessage(user));

//        根据com.example.config.ActiveMqJMSConfig.jmsTemplate配置自动找目的地
//        jmsOperations.send(
//                session -> session.createObjectMessage(user));

//        使用消息转换 (推荐)
        jmsOperations.convertAndSend(user);
    }

    @Override
    public User receiveUserAlert() {
        final Object o = jmsOperations.receiveAndConvert();
        return (User) o;
    }
}
