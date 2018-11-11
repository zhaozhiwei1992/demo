package com.lx.demo.springbootjdbc.controller;

import com.lx.demo.springbootjdbc.domain.User;
import com.lx.demo.springbootjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    /**
     * 可以使用构造方法自动注入
     */
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/add")
    public boolean register(@RequestParam String name){
        User user = new User();
        user.setName(name);
        return userRepository.insert(user);
    }

    @PostMapping("/user/save")
    public boolean register(@RequestBody User user){
        return userRepository.insert(user);
    }
}
