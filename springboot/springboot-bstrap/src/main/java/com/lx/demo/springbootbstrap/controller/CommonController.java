package com.lx.demo.springbootbstrap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    public class CommonController{

        @GetMapping("/common/echo")
        public String echo(String name){
            return "echo: " + name;
        }
    }
