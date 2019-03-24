package com.example.springcloudribbonserviceprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private Environment environment;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/profile/{key}")
    public String getProperties(@PathVariable String key){
        return environment.getProperty(key);
    }

    @GetMapping("/profile/appname")
    public String getApplicationName(){
        return applicationName;
    }
}
