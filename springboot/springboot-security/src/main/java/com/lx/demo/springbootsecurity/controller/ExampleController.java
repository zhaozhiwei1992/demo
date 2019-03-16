package com.lx.demo.springbootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class ExampleController {
    @GetMapping("/")
    public String index(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @GetMapping("/loginpage")
    public String login() {
        return "login";
    }
}
