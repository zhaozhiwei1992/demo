package com.example.springcloudribbonclient.controller;

import com.example.springcloudribbonclient.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${serivce-provider.host}")
    private String serviceProviderHost;

    @Value("${serivce-provider.port}")
    private Integer serviceProviderPort;

    @Value("${service-provider.name}")
    private String serviceProviderName;

    @GetMapping("")
    public String index(){
        User user = new User();
        user.setId(1L);
        user.setName("zhao");
        user.setAge(18);
        return restTemplate.postForObject(String.format("http://%s:%d/greeting", serviceProviderHost, serviceProviderPort),
                user, String.class);

//        return restTemplate.postForObject("http://" +
//                        serviceProviderName +
//                        "/greeting",
//                user, String.class);
    }
}
