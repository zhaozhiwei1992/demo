package com.example.api;

import com.example.domain.User;
import com.example.fallback.UserServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 对外提供服务的标准接口
 *
 * 加入feigh来提供便捷de远程访问
 */
@FeignClient(name = "${service.provider.name}", fallback = UserServiceFallback.class)
public interface UserService {

    @PostMapping("/users")
    boolean createUser(User user);

    @GetMapping("/users")
    List<User> getAllUser();
}
