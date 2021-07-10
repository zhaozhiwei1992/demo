package com.lx.demo.controller;

import com.lx.demo.service.PlaceHolderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceHolderController implements PlaceHolderInterface {

    @Value("${spring.application.name}")
    private String appname;

    @Autowired
    private PlaceHolderInterface placeHolder;

    /**
     * 增加produces, 解决返回前端汉字乱码问题
     * @return
     */
    @Override
    @GetMapping(value = "/echo", produces = "application/json; charset=UTF-8")
    public String echo(){
//        return appname;
        return placeHolder.echo();
    }
}
