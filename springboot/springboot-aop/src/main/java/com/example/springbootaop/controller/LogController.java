package com.example.springbootaop.controller;

import com.example.springbootaop.annotation.CustomLogAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @CustomLogAnnotation("我是log注解")
    @GetMapping("/showlog")
    public String showLog(){
        logger.info("当前位置--> com.example.springbootaop.controller.LogController.showLog");
        return "showlog";
    }

    @Autowired
    private LogControllerSub logControllerSub;

    /**
     * 传统方式拦截
     * @return
     */
    @GetMapping("/showlog2")
    public String showLog2(){
//        logger.info("当前位置--> com.example.springbootaop.controller.LogController.showLog2");
//        return "showlog2";
        return logControllerSub.showLog2();
    }

    /**
     * 传统方式拦截
     * 该方式可以拦截subcontroller
     * @return
     */
    @GetMapping("/showlog2/sub")
    public String showLogSub2(){
        return logControllerSub.showLog2();
    }
}
