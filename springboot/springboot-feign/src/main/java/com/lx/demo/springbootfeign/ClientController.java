package com.lx.demo.springbootfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-11-06 下午2:25
 */
@RestController
public class ClientController {

    @Autowired
    private HelloClientFeign feignClient;

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return feignClient.hello(name);
    }
}
