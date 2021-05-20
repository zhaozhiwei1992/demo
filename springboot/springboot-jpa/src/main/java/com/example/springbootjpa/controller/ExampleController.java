package com.example.springbootjpa.controller;

import com.example.springbootjpa.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootjpa.controller
 * @Description: TODO
 * @date 2021/5/20 下午9:26
 */
@RestController
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/query")
    public String query(){
       return exampleService.query();
    }
}
