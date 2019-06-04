package com.example.springboothessian.controller;

import com.example.springboothessian.web.hessian.SimpleHessian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class SimpleHessianController {

    @Autowired
    private SimpleHessian simpleHessian;

    @GetMapping("/testSayHello/{name}")
    public String testSayHello(@PathVariable("name") String name){
        return simpleHessian.sayHello(name);
    }
}
