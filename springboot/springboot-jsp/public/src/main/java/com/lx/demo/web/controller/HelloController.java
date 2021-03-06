package com.lx.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

@Controller
@RequestMapping("/public")
public class HelloController {

    /**
     * https://www.cnblogs.com/zhaolei1996/p/10700795.html
     * Servlet.service() for servlet [jsp] in context with path [] threw exception [java.lang.NullPointerException] with root cause
     * @param m
     * @return
     */
    @GetMapping("/hello")
    public String hello(Model m){
        m.addAttribute("date", DateFormat.getDateTimeInstance().format(new Date()));
        return "a/hello";
    }

    @GetMapping("/hello/core")
    public String helloCore(Model m){
        m.addAttribute("date", DateFormat.getDateTimeInstance().format(new Date()));
        return "a/b/hello";
    }

}
