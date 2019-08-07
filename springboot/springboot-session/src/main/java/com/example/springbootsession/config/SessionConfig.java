package com.example.springbootsession.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 如何在两台或者多台中共享 Session
 * 其实就是按照上面的步骤在另一个项目中再次配置一次，启动后自动就进行了 Session 共享。
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {

}

