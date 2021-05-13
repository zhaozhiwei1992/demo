package com.example.springbootperformanalysis.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.resource
 * @Description: TODO
 * @date 2021/5/12 下午8:21
 */
@RestController
public class MemResource {

    @GetMapping("/mem")
    public String mem(){
        // new 对象
        return "";
    }
}
