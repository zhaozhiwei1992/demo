package com.example.springbootjpa.controller;

import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/save")
    public User save(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/user/findall")
    public Collection<User> findAll(){
        return userRepository.findAll();
    }
}
