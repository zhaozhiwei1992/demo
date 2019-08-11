package com.lx.demo.springbootrabbitmq.direxchange.consumer;

import com.lx.demo.springbootrabbitmq.direxchange.producer.RabbitProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitConsumerTest {

    @Autowired
    private RabbitProducer producer;

    @Test
    public void msg() {
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(() -> {
                producer.sendMsgString();
                producer.sendMsgInt(new Random(10).nextInt());
            });
        }
    }
}