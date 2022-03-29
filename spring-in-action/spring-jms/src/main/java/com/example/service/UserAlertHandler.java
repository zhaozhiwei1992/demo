package com.example.service;

import com.example.domain.User;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description:
 * 如果bean实现了
 * MessageListener，那就没有必要再指定method属性了，默认就
 * 会调用onMessage()方法
 * {@see com.example.config.ActiveMqJMSConfig#messageListenerContainer(com.example.service.AlertService)}
 * @date 2022/3/29 下午6:03
 */
@Component
public class UserAlertHandler implements MessageListener {

    private String id;

    private CountDownLatch latch = new CountDownLatch(1);

    public UserAlertHandler(String id) {
        super();
        this.id = id;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("这里是自定义的AlertHandler");
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                System.out.printf("id='%s' received status='%s'", id, text);
                latch.countDown();
            } catch (JMSException e) {
                System.out.println("unable to read message payload");
                e.printStackTrace();
            }
        } else {
            System.out.println("received unsupported message type");
        }

        try {
            final Serializable object = ((ActiveMQObjectMessage) message).getObject();
            if(object instanceof User){
                User user = (User) object;
                System.out.println(user);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
