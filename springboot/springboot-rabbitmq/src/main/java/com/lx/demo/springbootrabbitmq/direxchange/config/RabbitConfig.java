package com.lx.demo.springbootrabbitmq.direxchange.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 创建一个叫hello的queue
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue("ttang");
    }
}
