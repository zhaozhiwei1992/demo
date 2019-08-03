package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 * @author zhaoz
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulProxyMain {
    public static void main( String[] args ) {
        SpringApplication.run(ZuulProxyMain.class, args);
    }
}
