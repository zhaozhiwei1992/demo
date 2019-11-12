package com.lx.demo.springbootfeign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-11-06 下午2:25
 */
@RestController
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private HelloClientFeign feignClient;

    @Value("${application.testip:127.0.0.1:9999}")
    private String ip;

    @GetMapping("/testip")
    public String testIp(){
        logger.info("testip {}", ip);
        return ip;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return feignClient.hello(name);
    }
}
