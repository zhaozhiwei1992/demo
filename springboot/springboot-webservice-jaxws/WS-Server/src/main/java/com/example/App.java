package com.example;

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
        SpringApplication.run(App.class, args);
    }
}
