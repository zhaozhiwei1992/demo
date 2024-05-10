package com.lx.demo.springbootthymeleaf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/echo")
    public String echo() {
        return "helloworld";
    }

}