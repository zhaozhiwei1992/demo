package com.example.spring;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalProduceController {

    @Autowired
    private RocketMQTemplate rocketmqTemplate;

    @GetMapping("/test")
    public String test() {
        String topic = "TestTopic";
        Message<String> msg = MessageBuilder.withPayload("Hello,RocketMQ").build();
        rocketmqTemplate.send(topic, msg);
        return "success";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(Integer number) {
        try {
            for (int i = 0; i < number; i++) {
                rocketmqTemplate.syncSend("TestTopic", "Message: " + i);
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * @param number :
     * @Description: 消息都怼一个上
     * 如果消息都怼一个queue(队列上)，测试发现只有一个消费者组中的一个消费者消费到消息, 这也是mq保证顺序的关键
     * @author: zhaozhiwei
     * @data: 2025/4/13-19:19
     * @return: java.lang.String
     */

    @PostMapping("/sendMessage4one")
    public String sendMessage4One(Integer number) {
        MessageQueueSelector selector = (mqs, msg, arg) -> {
            // 根据订单ID选择消息队列
//      int orderIdToUse = (int) arg;
//      int index = orderIdToUse % mqs.size();
//      return mqs.get(index);
            // 就怼这个0
            return mqs.get(0);
        };

        rocketmqTemplate.setMessageQueueSelector(selector);
        try {
            for (int i = 0; i < number; i++) {
                // 查看rocketmq dashboard 查看topic信息, 发现只有queueId为0的队列maxoffset后移
                rocketmqTemplate.sendOneWayOrderly("TestTopic", "Message: " + i, "0");
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}