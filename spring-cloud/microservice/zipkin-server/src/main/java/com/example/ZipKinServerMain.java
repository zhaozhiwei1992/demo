package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.internal.EnableZipkinServer;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class ZipKinServerMain {
    public static void main(String[] args) {
        SpringApplication.run(ZipKinServerMain.class);
    }
}
