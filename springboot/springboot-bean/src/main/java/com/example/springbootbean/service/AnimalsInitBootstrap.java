package com.example.springbootbean.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration("annotationBeanInit")
@ComponentScan(basePackages = "com.example.springbootbean.service")
public class AnimalsInitBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnimalsInitBootstrap.class);

        final AnimalService bean = context.getBean(AnimalService.class);
        System.out.println(bean.animals());

        context.close();
    }

}
