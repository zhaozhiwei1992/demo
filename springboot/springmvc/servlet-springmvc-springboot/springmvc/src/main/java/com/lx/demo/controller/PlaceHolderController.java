package com.lx.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceHolderController {

    @Value("${spring.application.name}")
    private String appname;

    /**
     * 增加produces, 解决返回前端汉字乱码问题
     * @return
     */
    @GetMapping(value = "/echo", produces = "application/json; charset=UTF-8")
    public String echo(){
        return appname;
    }
}
