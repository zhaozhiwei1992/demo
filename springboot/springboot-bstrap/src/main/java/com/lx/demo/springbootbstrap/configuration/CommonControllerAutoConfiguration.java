package com.lx.demo.springbootbstrap.configuration;

import com.lx.demo.springbootbstrap.controller.CommonController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

@ConditionalOnWebApplication
public class CommonControllerAutoConfiguration {

//    @Bean
//    public CommonController commonController(){
//        return new CommonController();
//    }
}
