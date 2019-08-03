package com.lx.demo;

import com.lx.demo.controller.UserController;
import com.lx.demo.resource.SayHelloImpl;
import com.lx.demo.resource.SayHelloImpl2;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Hello world!
 */
@SpringBootApplication
public class SearchExtMain {
    public static void main(String[] args) {
        SpringApplication.run(SearchExtMain.class);
    }

    @Autowired
    private Bus bus;

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/");
        // Register 2 JAX-RS root resources supporting "/sayHello/{id}" and "/sayHello2/{id}" relative paths
//        endpoint.setServiceBeans(Arrays.<Object>asList(new HelloServiceImpl1(), new HelloServiceImpl2()));
        endpoint.setServiceBeans(Arrays.<Object>asList(new SayHelloImpl(), new SayHelloImpl2(), new UserController()));
//        endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        return endpoint.create();
    }
}
