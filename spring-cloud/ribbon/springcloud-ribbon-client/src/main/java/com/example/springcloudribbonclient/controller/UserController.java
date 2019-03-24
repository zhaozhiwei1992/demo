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

    @Value("${service-provider.host}")
    private String serviceProviderHost;

    @Value("${service-provider.port}")
    private Integer serviceProviderPort;

    @Value("${service-provider.name}")
    private String serviceProviderName;


    @GetMapping("")
    public String index() {
        User user = new User();
        user.setId(1L);
        user.setName("zhao");
        user.setAge(18);
        // 這個方式不用ribbon就可以用
//        return restTemplate.postForObject(String.format("http://%s:%d/greeting", serviceProviderHost, serviceProviderPort),
//                user, String.class);

        // 使用ribbon就可以用實例來訪問了
        return restTemplate.postForObject("http://" +
                        serviceProviderName +
                        "/greeting",
                user, String.class);
    }
}
