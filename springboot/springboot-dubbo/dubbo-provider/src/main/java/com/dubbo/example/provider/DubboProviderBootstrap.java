package com.dubbo.example.provider;

import com.dubbo.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;


@EnableAutoConfiguration
public class DubboProviderBootstrap {

    @Reference(version = "1.0.0")
    private DemoService demoService;
//
//    非dubboservice, 不能通过reference引用
    @Reference
    private DemoService2 demoService2;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderBootstrap.class)
                .run(args);
        System.out.println("xx");
    }

    @Bean
    public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor(){
        final AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor =
                new AutowiredAnnotationBeanPostProcessor();

        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new HashSet();
        autowiredAnnotationTypes.add(Autowired.class);
        autowiredAnnotationTypes.add(Value.class);
//        dubbo reference作为autowired类型， 可以同时兼容dubbo外部引入和spring本地bean注入方式
        autowiredAnnotationTypes.add(com.alibaba.dubbo.config.annotation.Reference.class);

        try {
            autowiredAnnotationTypes.add((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Inject", AutowiredAnnotationBeanPostProcessor.class.getClassLoader()));
        } catch (ClassNotFoundException var3) {
        }
        autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return autowiredAnnotationBeanPostProcessor;
    }
}