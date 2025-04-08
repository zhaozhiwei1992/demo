package com.example.spring;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
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
}