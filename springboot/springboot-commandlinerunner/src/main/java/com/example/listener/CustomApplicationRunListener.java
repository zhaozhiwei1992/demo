package com.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Title: CustomApplicationRunListener
 * @Package com/example/listener/CustomApplicationRunListener.java
 * @Description:
 * 这个的优先级，高于commandLineRunner和 ApplicationRunner
 * @author zhaozhiwei
 * @date 2022/7/8 上午9:46
 * @version V1.0
 */
public class CustomApplicationRunListener implements SpringApplicationRunListener {

    public CustomApplicationRunListener(SpringApplication application, String[] args) {

    }

    private static final Logger log = LoggerFactory.getLogger(CustomApplicationRunListener.class);

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
        log.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
