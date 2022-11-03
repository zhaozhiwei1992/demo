package com.example.springboottest.configuration;

import com.example.springboottest.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springboottest
 * @Description: TODO
 * @date 2021/10/20 下午3:35
 */
@Configuration
public class UserConfiguration {

    @Bean
    public User user(){
        final User user = new User();
        user.setId(1L);
        user.setAge(18);
        user.setName("ttang");
        return user;
    }

}
