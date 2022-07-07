package com.example.service;

import com.example.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Title: UserServiceFeignHystrix
 * @Package com/example/service/UserServiceFeignHystrix.java
 * @Description: 引入了api后, 已经默认增加了FeignClient接口, 这里再初始化就会造成冲突, so,去掉
 * @author zhaozhiwei
 * @date 2022/6/18 下午3:35
 * @version V1.0
 */
//@FeignClient(name = "${service.provider.name}", fallback=UserServiceFeignHystrixFallback.class)
public interface UserServiceFeignHystrix {

    /**
     * test hystrix timeout
     * @return
     */
    @GetMapping("/users")
    List<User> getAllUser();

}
