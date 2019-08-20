package com.lx.demo.springbootdocker.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    /**
     * curl -X GET http://localhost:8080/
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "Hello World !";
    }
}
