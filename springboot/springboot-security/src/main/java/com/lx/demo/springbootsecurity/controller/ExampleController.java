package com.lx.demo.springbootsecurity.controller;

import com.lx.demo.springbootsecurity.configuration.SecurityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

/**
 * @Title: ExampleController
 * @Package com/lx/demo/springbootsecurity/controller/ExampleController.java
 * @Description: 不需要登录的都放到这里, 需要在{@link SecurityConfiguration} 放开权限
 * @author zhaozhiwei
 * @date 2021/6/5 下午9:38
 * @version V1.0
 */
@Controller
public class ExampleController {

    /**'
     * security校验完成后 也会访问改路径，相当于系统默认路径
     * @param model
     * @return
     */
    @GetMapping("")
    public String index(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "index";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
