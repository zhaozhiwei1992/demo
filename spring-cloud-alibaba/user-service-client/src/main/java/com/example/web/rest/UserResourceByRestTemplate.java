package com.example.web.rest;

import com.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 通过resttemplate方式获取用户信息
 */
@RestController
@Slf4j
@RequestMapping("/resttemplate")
public class UserResourceByRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.provider.name}")
    private String providerInstanceName;

    /**
     * 查找所有用户
     * resttemplate请求会负载均衡, 原理类似下面choose，挑了一个
     *
     * 这里应该是通过网关再次分发，还是业务自行去分发..
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/users")
    public List<User> getAllUser() throws InterruptedException {
        return restTemplate.getForObject("http://"+providerInstanceName+"/users", List.class);
    }
}
