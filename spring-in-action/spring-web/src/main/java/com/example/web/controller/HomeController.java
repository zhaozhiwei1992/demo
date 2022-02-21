package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.web.controller
 * @Description:
 * 通过后端服务访问到home首页
 * 需要配置解析器
 * @date 2022/2/21 上午9:10
 */
@Controller("/")
public class HomeController {

    @GetMapping
    public String homePage(){
        return "home";
    }

}
