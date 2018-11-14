package com.lx.demo.springbootvalidate.controller;

import com.lx.demo.springbootvalidate.domain.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /**
     * curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":1}' http://127.0.0.1:8080/user/save
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public User saveUser(@Validated @RequestBody User user){
         return user;
    }
}
