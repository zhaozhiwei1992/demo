package com.example.springbootaop.controller;

import com.example.springbootaop.annotation.CustomLogAnnotation;
import com.example.springbootaop.service.InnerMethodAspectService;
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

    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 传统方式拦截
     * @return
     */
    @GetMapping("/showlog2")
    public String showLog2(){
//        logger.info("当前位置--> com.example.springbootaop.controller.LogController.showLog2");
//        return "showlog2";
        logger.info("通过反射设置msg, 值: {}", msg);
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

    @Autowired
    private InnerMethodAspectService innerMethodAspectService;

    @GetMapping("/showlog/inner")
    public String innerMethodLog(){
        innerMethodAspectService.someMethod();
        return "success";
    }
}
