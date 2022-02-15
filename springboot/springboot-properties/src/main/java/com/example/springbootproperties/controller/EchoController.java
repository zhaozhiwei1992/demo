package com.example.springbootproperties.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态修改参数
 */
@RestController
@Log4j2
public class EchoController {

    @Value("${msg}")
    private String msg;

    /**
     * curl -X GET http://127.0.0.1:8080/echo/msg
     * @return
     */
    @GetMapping("/echo/msg")
    private String echo(){
        log.info("日志输出 {}", msg);
        return msg;
    }

    /**
     * @data: 2022/2/15-下午3:15
     * -Da=xxx -Db=${a}  增加这样参数也是生效的
     */
    @Value("${b}")
    private String b;

    @GetMapping("/echo/b")
    private String b(){
        log.info("日志输出 {}", b);
        return b;
    }
}
