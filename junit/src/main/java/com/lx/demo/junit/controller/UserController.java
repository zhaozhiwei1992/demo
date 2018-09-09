package com.lx.demo.junit.controller;

import com.lx.demo.junit.domain.User;
import com.lx.demo.junit.service.UserRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRemoteService userRemoteService;

    public List<User> findAll(){
        return userRemoteService.findAll();
    }
}
