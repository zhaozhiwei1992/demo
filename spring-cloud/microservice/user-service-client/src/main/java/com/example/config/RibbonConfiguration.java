package com.example.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 改配置会改变ribbon规则, 使用随机
 */
@Configuration
public class RibbonConfiguration {

//    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
