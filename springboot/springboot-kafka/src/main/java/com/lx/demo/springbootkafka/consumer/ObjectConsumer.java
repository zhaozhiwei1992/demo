package com.lx.demo.springbootkafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ObjectConsumer {

    @KafkaListener(topics = "saveUser")
    public void processMessage(Object object){
        System.out.println(object);
    }
}
