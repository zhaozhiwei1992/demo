package com.example.httpsserver.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    /**
     *curl http://127.0.0.1:8080/index
     * @return
     */
    @GetMapping("/index")
    public String index(){
        System.out.println("请求成功了");
        return "This is HTTPS Response";
    }
}
