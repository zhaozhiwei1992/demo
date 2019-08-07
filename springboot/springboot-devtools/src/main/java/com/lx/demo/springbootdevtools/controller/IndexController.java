package com.lx.demo.springbootdevtools.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    /**
     * 这里属性不加注解会自动解析
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(String name){
        return "helloxx: " + name;
    }

    /**
     * 假如新方法编译后，　devtools自动重启
     * Applications that use spring-boot-devtools automatically restart whenever files on the classpath change
     * @param name
     * @return
     */
    @GetMapping("/hello2")
    public String hello2(String name){
        return "hello2: " + name;
    }
}
