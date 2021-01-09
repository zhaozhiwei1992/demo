package com.example.springbootaop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogControllerSub {

    private static final Logger logger = LoggerFactory.getLogger(LogControllerSub.class);

    /**
     * 传统方式拦截, 测试内部调用是否可以拦截
     * @return
     */
    @GetMapping("/showlogsub2")
    public String showLog2(){
        logger.info("当前位置--> com.example.springbootaop.controller.LogControllerSub.showLog2");
        return "showlog2";
    }
}
