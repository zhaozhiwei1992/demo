package com.example.springbootproperties.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootproperties.controller
 * @Description: 获取配置，并解析成list
 * @date 2021/6/7 下午8:24
 */
@RestController
public class ListController {

    @Value("#{'${auth_whitelist}'.split(',')}")
    private List authWhitelist;

    @GetMapping("writelist")
    public List writelist(){
       return authWhitelist;
    }
}
