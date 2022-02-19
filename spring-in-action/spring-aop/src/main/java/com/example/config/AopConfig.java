package com.example.config;

import com.example.aop.JumpIntroducer;
import com.example.aop.RunTimer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/2/19 下午11:25
 */
//在配置类的类级别上通过使
//用EnableAspectJ-AutoProxy注解启用自动代理功能
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public RunTimer runTimer(){
        return new RunTimer();
    }

//    @Bean
//    public JumpIntroducer jumpIntroducer(){
//        return new JumpIntroducer();
//    }
}
