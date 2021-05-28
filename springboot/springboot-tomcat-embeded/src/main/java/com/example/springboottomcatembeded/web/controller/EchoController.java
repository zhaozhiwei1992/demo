package com.example.springboottomcatembeded.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springboottomcatembeded.web.controller
 * @Description: TODO
 * @date 2021/5/27 下午11:45
 */
@RestController
public class EchoController {

    @GetMapping("/echo/{msg}")
    public String echo(@PathVariable String msg){
        return "echo: " + msg;
    }
}
