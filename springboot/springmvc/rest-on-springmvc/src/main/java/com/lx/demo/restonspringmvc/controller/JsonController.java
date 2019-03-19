package com.lx.demo.restonspringmvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class JsonController {

    /**
     * curl http://127.0.0.1:8080/demo/json/11
     * 2018-12-31 20:19:53.698  WARN 10670 --- [nio-8080-exec-2] .w.s.m.s.DefaultHandlerExceptionResolver :
     * Failed to write HTTP message: org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class java.lang.String
     * @param message
     * @return
     */
    @RequestMapping(value = "/demo/json/{message}")
    @ResponseBody
    public String returnJsonPathVariable(@PathVariable String message){
        return "{\"message\" :" + message + "}";
    }

}
