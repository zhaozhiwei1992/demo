package com.lx.demo.controller;

import com.lx.demo.domain.User;
import com.lx.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return userRepository.save(user) > 0;
    }

    @PostMapping("/user/save")
    public boolean register(@RequestBody User user){
        return userRepository.save(user) > 0;
    }

    @GetMapping("/user/all")
    public List<User> findAll(){
        return userRepository.findALL();
    }
}
