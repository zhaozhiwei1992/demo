package com.lx.demo.springbootrabbitmq.fanoutexchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义多个queue, 一个Fanoutexchange, 配置绑定binding
 * 这样发送到交换机上的消息都会传递到所有绑定queue
 *
 * 这个最像传统的主题订阅模式
 */
@Configuration
public class FanoutExchangeConfig {

    /**
     * 创建交换机
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 创建queue
     * @return
     */
    @Bean
    Queue fanoutQueue1(){
        return new Queue("fanout.1");
    }

    /**
     * 这里不在需要指定binding_key, 就算配了也么得用
     * @param fanoutQueue1
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding binding1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    Queue fanoutQueue2(){
        return new Queue("fanout.2");
    }

    @Bean
    Binding binding2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    @Bean
    Queue fanoutQueue3(){
        return new Queue("fanout.3");
    }

    /**
     * Description:
     *
     * Parameter 0 of method binding1 in com.lx.demo.springbootrabbitmq.fanoutexchange.config.FanoutExchangeConfig
     * required a single bean, but 6 were found:
     * 	- queue: defined by method 'queue' in class path resource
     * 	[com/lx/demo/springbootrabbitmq/direxchange/config/RabbitConfig.class]
     * 	- fanoutQueue1: defined by method 'fanoutQueue1' in class path resource
     * 	[com/lx/demo/springbootrabbitmq/fanoutexchange/config/FanoutExchangeConfig.class]
     * 	- fanoutQueue2: defined by method 'fanoutQueue2' in class path resource
     * 	[com/lx/demo/springbootrabbitmq/fanoutexchange/config/FanoutExchangeConfig.class]
     * 	- fanoutQueue3: defined by method 'fanoutQueue3' in class path resource
     * 	[com/lx/demo/springbootrabbitmq/fanoutexchange/config/FanoutExchangeConfig.class]
     * 	- queueMessage: defined by method 'queueMessage' in class path resource
     * 	[com/lx/demo/springbootrabbitmq/tpcexchange/config/TopicExchangeConfig.class]
     * 	- queueMessages: defined by method 'queueMessages' in class path resource
     * 	[com/lx/demo/springbootrabbitmq/tpcexchange/config/TopicExchangeConfig.class]
     *
     * @param fanoutQueue3
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding binding3(Queue fanoutQueue3, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange);
    }

}
