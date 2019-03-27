package com.example.springbootjpa.controller;

import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.repository.UserRepository;
import com.example.springbootjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * curl -H "Content-Type:application/json;charset=UTF-8" -X POST -d '{"name":"lisi", "age":18, "password":"1", "books":[{"id":5}, {"id":6}]}' http://127.0.0.1:8080/user/save
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/user/findall")
    public Collection<User> findAll(){
        return userService.findAll();
    }


    @Autowired
    private UserRepository userRepository;

    /**
     * curl 127.0.0.1:8080/user/findbysql
     * @return
     */
    @GetMapping("/user/findbysql")
    public Collection<User> findBySQL(){
        return userRepository.findBySql();
    }
}
