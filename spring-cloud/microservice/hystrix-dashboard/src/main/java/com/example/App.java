package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 *
 * 作為hystrix的dashboard
 */
@SpringBootApplication
@EnableHystrixDashboard
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
