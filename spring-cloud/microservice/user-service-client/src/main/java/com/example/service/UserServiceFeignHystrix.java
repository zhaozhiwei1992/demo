package com.example.service;

import com.example.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "${service.provider.name}", fallback=UserServiceFeignHystrixFallback.class)
public interface UserServiceFeignHystrix {

    /**
     * test hystrix timeout
     * @return
     */
    @GetMapping("/users")
    List<User> getAllUser();

}
