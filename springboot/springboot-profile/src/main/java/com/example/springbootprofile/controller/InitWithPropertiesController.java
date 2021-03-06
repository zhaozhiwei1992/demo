package com.example.springbootprofile.controller;

import com.example.springbootprofile.domain.InitWithCustomProperties;
import com.example.springbootprofile.domain.InitWithProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties(InitWithProperties.class)
public class InitWithPropertiesController {

    @Autowired
    private InitWithCustomProperties initWithCustomProperties;

    /**
     * curl -X GET http://localhost:8080/email/properties
     * {"from":"xx","to":"oo"}%
     * @return
     */
    @GetMapping("/email/properties")
    public InitWithCustomProperties getInitWithCustomProperties(){
        return initWithCustomProperties;
    }

    @Autowired
    private InitWithProperties initWithProperties;

    @GetMapping("/zhao/properties")
    public InitWithProperties getProperties(){
        return initWithProperties;
    }
}
