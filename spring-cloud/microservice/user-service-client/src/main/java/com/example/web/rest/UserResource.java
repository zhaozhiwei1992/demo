package com.example.web.rest;

import com.example.api.UserService;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户服务客户端，真正跟外界对接的服务
 * 通过feigh的方式来调用服务
 *
 * 这里不推荐实现userservice接口的方式，实现接口可以继承接口的注解
 */
@RestController
public class UserResource{

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    boolean createUser(@RequestBody  User user){
        return userService.createUser(user);
    }

    @GetMapping("/users")
    List<User> getAllUser(){
        return userService.getAllUser();
    }
}

