package com.example.springbootperformanalysis.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.controller
 * @Description: TODO
 * @date 2021/5/12 下午8:38
 */
@RestController
public class EchoController {

    /**
     * @data: 2021/5/12-下午8:55
     * @User: zhaozhiwei
     * @method: echo
      * @param msg :
     * @return: java.lang.String
     * @Description: 描述
     * curl -X GET http://127.0.0.1:8080/echo\?msg\=xx
     */
    @GetMapping("/echo")
    public String echo(@RequestParam String msg){
        System.out.println("echo: " + msg);
        return msg;
    }
}
