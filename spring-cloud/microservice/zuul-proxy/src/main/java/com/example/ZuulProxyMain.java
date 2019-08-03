package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        // 通过下边方式resttemplate 支持超时
        final SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(1000);
        simpleClientHttpRequestFactory.setReadTimeout(1000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }
}
