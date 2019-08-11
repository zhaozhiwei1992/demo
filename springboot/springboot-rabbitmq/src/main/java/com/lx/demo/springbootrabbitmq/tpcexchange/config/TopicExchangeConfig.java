package com.lx.demo.springbootrabbitmq.tpcexchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 需要三个bean实例 queue, exchange, bindexchange
 *
 * queue1比queue2只有一个字母之差，模拟匹配
 */
@Configuration
public class TopicExchangeConfig {

    /**
     * 定制主题
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Queue queueMessage(){
        return new Queue("topic.message");
    }

    /**
     * 精确匹配topic.message
     * @param queueMessage
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingMessage(Queue queueMessage, TopicExchange topicExchange){
        return BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
    }

    @Bean
    public Queue queueMessages(){
        return new Queue("topic.messages");
    }

    /**
     * 匹配多个
     * *表示一个词.
     * #表示零个或多个词.
     *
     * 这个queue能接收多个消息
     * @param queueMessages
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingMessages(Queue queueMessages, TopicExchange topicExchange){
        return BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");
    }
}
