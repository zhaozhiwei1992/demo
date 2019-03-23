package com.example.springcloudclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springcloudclient.domain.User;

@RestController
@EnableConfigurationProperties(User.class)
public class UserController{

    @Autowired
    private User user;

    @GetMapping("/user")
    public User getUser(){
        return user;
    }
}