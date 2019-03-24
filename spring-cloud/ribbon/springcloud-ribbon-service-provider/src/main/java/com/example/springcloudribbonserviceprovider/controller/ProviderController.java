package com.example.springcloudribbonserviceprovider.controller;

import com.example.springcloudribbonserviceprovider.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @PostMapping("/greeting")
    public String greeting(@RequestBody User user){

        logger.info("传入用户信息: " + user.toString());
        return user.toString();
    }
}
