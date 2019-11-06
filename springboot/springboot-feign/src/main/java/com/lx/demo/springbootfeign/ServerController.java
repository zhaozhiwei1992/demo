package com.lx.demo.springbootfeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-11-06 下午2:25
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return "hello: " + name;
    }
}
