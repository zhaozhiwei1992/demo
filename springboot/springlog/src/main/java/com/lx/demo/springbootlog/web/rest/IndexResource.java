package com.lx.demo.springbootlog.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexResource {

    @GetMapping("/")
    public String index(){
        return "xx";
    }
}
