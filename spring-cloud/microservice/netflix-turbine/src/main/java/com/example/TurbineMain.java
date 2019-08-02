package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableTurbine
public class TurbineMain {
    public static void main(String[] args) {
        SpringApplication.run(TurbineMain.class);
    }
}
