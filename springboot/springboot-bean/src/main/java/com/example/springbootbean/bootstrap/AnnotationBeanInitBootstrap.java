package com.example.springbootbean.bootstrap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

//@SpringBootApplication
@Configuration("annotationBeanInit")
@ComponentScan(basePackages = "com.example.springbootbean.bootstrap")
public class AnnotationBeanInitBootstrap {

    public static void main(String[] args) {
        // 构建 Annotation 配置驱动 Spring 上下文
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        // 注册 当前引导类（被 @Configuration 标注） 到 Spring 上下文
//        context.register(AnnotationBeanInitBootstrap.class);
//        // 启动上下文 Exception in thread "main" java.lang.IllegalStateException: org.springframework.context.annotation.AnnotationConfigApplicationContext@77556fd has not been refreshed yet
//        context.refresh();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationBeanInitBootstrap.class);

        String zhangsan = String.valueOf(context.getBean("lisi"));
        System.out.println(zhangsan);

        //所有注册得bean
        //->org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        //->org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        //->org.springframework.context.annotation.internalCommonAnnotationProcessor
        //->org.springframework.context.event.internalEventListenerProcessor
        //->org.springframework.context.event.internalEventListenerFactory
        //->annotationBeanInit
        //->zhangsan
        //->lisi
        Stream.of(context.getBeanDefinitionNames()).map(name -> "->" + name).forEach(System.out::println);
        context.close();
    }

    @Bean
    public String zhangsan(){
        return "张三";
    }

    @Bean
    public String lisi(){
        return "李四";
    }
}
