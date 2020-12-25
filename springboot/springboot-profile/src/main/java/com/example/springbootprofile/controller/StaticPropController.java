package com.example.springbootprofile.controller;

import com.example.springbootprofile.domain.StaticProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootprofile.controller
 * @Description: 测试静态变量初始化
 * @date 2020/12/25 上午10:33
 */
@RestController
public class StaticPropController {

    @Autowired
    private StaticProperties staticProperties;

    @GetMapping("/staprop")
    public String getStaProp(){
        return staticProperties.getStaticProp();
    }
}
