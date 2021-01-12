package com.example.nacosexample.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Title: TestController
 * @Package com/example/nacosexample/web/controller/TestController.java
 * @Description: 测试负载均衡
 * @author zhaozhiwei
 * @date 2021/1/12 下午3:30
 * @version V1.0
 */
@RestController
@RequestMapping("/consumer")
@RefreshScope
public class TestController {

    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-example/echo/" + str, String.class);
    }

    @Value("${ifmis.test2:def-test2}")
    private String test2;

    @RequestMapping("/test2")
    public String test2(){
        return test2;
    }

    @Value("${ifmis.test3:def-test3}")
    private String test3;

    @RequestMapping("/test3")
    public String test3(){
        return test2;
    }
}