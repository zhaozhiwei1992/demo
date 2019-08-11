package com.lx.demo.springbootrabbitmq.tpcexchange.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicExchangeProducerTest {

    @Autowired
    private TopicExchangeProducer topicExchangeProducer;

    /**
     * 2019-08-11 12:54:46.482  INFO 18374 --- [           main] c.l.d.s.t.p.TopicExchangeProducer        :
     * sendMessage, 我爱你　糖糖
     * <p>
     * <p>
     * 2019-08-11 12:54:46.502  INFO 18374 --- [ntContainer#2-1] c.l.d.s.t.c.TopicConsumerMessages        :
     * receiveMsgString, com.lx.demo.springbootrabbitmq.tpcexchange.producer.TopicExchangeProducer,sendMessage,我爱你　糖糖
     * 2019-08-11 12:54:46.503  INFO 18374 --- [ntContainer#1-1] c.l.d.s.t.consumer.TopicConsumerMessage  :
     * receiveMsgString, com.lx.demo.springbootrabbitmq.tpcexchange.producer.TopicExchangeProducer,sendMessage,我爱你　糖糖
     * 这里producer发送用的routing_key topic.message匹配topic.message和topic.#, 所以可以干到两个queue里, 相应的消费两个queue的都能接收到
     */
    @Test
    public void sendMessage() {
        topicExchangeProducer.sendMessage("我爱你　糖糖");
    }

    /**
     * 2019-08-11 12:57:00.110  INFO 18687 --- [           main] c.l.d.s.t.p.TopicExchangeProducer        :
     * sendMessages, i love you, ttang
     * <p>
     * <p>
     * 2019-08-11 12:57:00.128  INFO 18687 --- [       Thread-2] o.s.a.r.l.SimpleMessageListenerContainer : Waiting
     * for workers to finish.
     * 2019-08-11 12:57:00.128  INFO 18687 --- [ntContainer#2-1] c.l.d.s.t.c.TopicConsumerMessages        :
     * receiveMsgString, com.lx.demo.springbootrabbitmq.tpcexchange.producer.TopicExchangeProducer,sendMessages,i
     * love you, ttang
     * messages的消息发送者用的routing_key只能匹配topic.#, 所以只能到topic.messages这个queue
     */
    @Test
    public void sendMessages() {
        topicExchangeProducer.sendMessages("i love you, ttang");
    }
}