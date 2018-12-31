package com.lx.demo.restonspringmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HTMLController {

    /**
     * request header accetp: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*
     * response head contenttype 返回什么， 浏览器根据权重进行解析 Content-Type: application/json;charset=UTF-8
     * @param message
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/demo/html/{message}"
//    , produces = MediaType.APPLICATION_JSON_UTF8_VALUE //浏览器默认解析xml, 如果返回json那就是字符串了
    )
    public String toHtml(@PathVariable String message){
        return "<html><head></head><body><h1>hello ," + message + " </h1></body></html>";
    }
}
