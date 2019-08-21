package com.lx.demo.springbooti18n.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * 秒了个鸡儿的默认的方式设置请求头不管用
     * 默认解析器通过请求中的accept-language处理
     * {@see AcceptHeaderLocaleResolver}
     ➜  ~ curl -H "Accept-Language: en" -X GET http://127.0.0.1:8080            <<<
     * @return
     */
    @GetMapping("/")
    public String index(){
       return "index";
    }
}
