package com.example;

import com.example.listener.PublishWsByEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 * https://github.com/spring-projects/spring-boot/blob/2.1.x/spring-boot-samples/spring-boot-sample-webservices/pom.xml
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="com.example")
public class App {
    public static void main( String[] args ) {

        final SpringApplication springApplication = new SpringApplication(App.class);
        // 通过@Component初始化更方便
//        springApplication.addListeners(new PublishWsByEndpoint());
        springApplication.run(args);
//        SpringApplication.run(App.class, args);
    }
}
