package com.example.springbootprofile.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @link https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#production-ready-process-monitoring-configuration
 * @link https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#boot-features-application-events-and-listeners
 */
public class CustomizedSpringBootApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedSpringBootApplicationListener.class);
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        // 这里也可以进行一些环境变量初始化
        System.err.println("自定义监听: " + environment.getActiveProfiles());
    }
}
