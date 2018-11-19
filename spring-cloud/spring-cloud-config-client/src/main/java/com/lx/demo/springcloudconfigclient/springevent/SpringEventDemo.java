package com.lx.demo.springcloudconfigclient.springevent;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring 自定义监听
 * 监听器与发布事件多对多
 *
 * {@link ApplicationEnvironmentPreparedEvent}
 * {@link ConfigFileApplicationListener}
 * https://docs.spring.io/spring-boot/docs/2.1.1.BUILD-SNAPSHOT/reference/htmlsingle/ --> 24. Externalized Configuration
 */
public class SpringEventDemo {

    public static void main(String[] args) {

        // annoation驱动上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册监听器, spring发布事件都会被监听器接受
        applicationContext.addApplicationListener((applicationEvent -> {
            System.out.println("接受到事件: " + applicationEvent.getSource());
        }));

        applicationContext.addApplicationListener((applicationEvent -> {
            System.out.println("2接受到事件: " + applicationEvent.getSource());
        }));

        //Exception in thread "main" java.lang.IllegalStateException: ApplicationEventMulticaster not initialized - call 'refresh' before multicasting events via the context: org.springframework.context.annotation.AnnotationConfigApplicationContext@2280cdac, started on Thu Jan 01 08:00:00 CST 1970
        //内部发布事件
        applicationContext.refresh();

        //发布事件
        applicationContext.publishEvent(new ApplicationEvent("hello world") {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });

//        applicationContext.publishEvent(new ApplicationEvent("hello world2") {
//            @Override
//            public Object getSource() {
//                return super.getSource();
//            }
//        });

        //监听器得到事件
    }

}
