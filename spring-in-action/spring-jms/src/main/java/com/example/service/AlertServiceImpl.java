package com.example.service;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Service;

@Service(value = "localAlertService")
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

    /**
     * @data: 2022/3/29-下午6:02
     * @User: zhaozhiwei
     * @method: receiveUserAlert

     * @return: com.example.domain.User
     * @Description: 描述
     * 使用JmsTemplate接收消息的最大缺点在于receive()和
     * receiveAndConvert()方法都是同步的。这意味着接收者必须耐
     * 心等待消息的到来，因此这些方法会一直被阻塞，直到有可用消息
     * （或者直到超时）。同步接收异步发送的消息，是不是感觉很怪
     */
    @Override
    public User receiveUserAlert() {
        final Object o = jmsOperations.receiveAndConvert();
        return (User) o;
    }
}
