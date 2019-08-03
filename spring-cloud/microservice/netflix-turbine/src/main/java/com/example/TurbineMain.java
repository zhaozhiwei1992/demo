package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * Hello world!
 */
@SpringBootApplication
//@EnableTurbine
@EnableTurbineStream
public class TurbineMain {
    public static void main(String[] args) {
        SpringApplication.run(TurbineMain.class);
    }
}
