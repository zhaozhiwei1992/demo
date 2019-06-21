package com.example.springbootjwt.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/login")
    public String login(){
       return "login" ;
    }

    @GetMapping("/index")
    public String index(){
        return "index" ;
    }
}
