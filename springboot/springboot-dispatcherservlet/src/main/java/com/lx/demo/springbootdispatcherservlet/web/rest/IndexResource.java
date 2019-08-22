package com.lx.demo.springbootdispatcherservlet.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexResource {

    @GetMapping("/index")
    public String index(){
        return "我是index";
    }
}
