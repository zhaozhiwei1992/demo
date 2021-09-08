package com.example.springbootskywalking.controller;

import com.example.springbootskywalking.service.TestService1;
import com.example.springbootskywalking.service.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootskywalking.controller
 * @Description: TODO
 * @date 2021/9/8 下午2:13
 */
@RestController
public class TestController {

    @Autowired
    private TestService1 testService1;

    @GetMapping("/test1")
    public String test1(){
        TestService1.staticMethod();
        testService1.method();
        return "success";
    }

    @Autowired
    private TestService2 testService2;

    @GetMapping("/test2")
    public String test2(){
        testService2.method(new Object());
        return "success";
    }

}
