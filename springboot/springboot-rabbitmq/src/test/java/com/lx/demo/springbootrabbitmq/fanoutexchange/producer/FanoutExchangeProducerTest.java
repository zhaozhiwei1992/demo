package com.lx.demo.springbootrabbitmq.fanoutexchange.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutExchangeProducerTest {

    @Autowired
    private FanoutExchangeProducer fanoutExchangeProducer;

    /**
     * 2019-08-11 13:48:16.402  INFO 26183 --- [           main] c.l.d.s.f.p.FanoutExchangeProducer       : sendMsg,
     * 大渣吼，我是范二特
     *
     *
     * 2019-08-11 13:48:16.425  INFO 26183 --- [ntContainer#2-1] c.l.d.s.f.consumer.FanoutConsumer2       :
     * receiveMessageString, 大渣吼，我是范二特
     * 2019-08-11 13:48:16.425  INFO 26183 --- [ntContainer#1-1] c.l.d.s.f.consumer.FanoutConsumer1       :
     * receiveMessageString, 大渣吼，我是范二特
     * 2019-08-11 13:48:16.425  INFO 26183 --- [ntContainer#3-1] c.l.d.s.f.consumer.FanoutConsumer3       :
     * receiveMessageString, 大渣吼，我是范二特
     *
     * 发送一条消息，上述三个消费者都收到了消息
     */
    @Test
    public void sendMsg() {
        fanoutExchangeProducer.sendMsg("大渣吼，我是范二特");
    }
}