package com.example.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实现访问http://127.0.0.1:8080/
 *
 * 1. spring boot 默认不支持jsp的，需要导入tomcat-embed-jasper
 * 2. 需要在application.properties中配置：
 *      spring.mvc.view.prefix=/WEB-INF/jsp/
 *      spring.mvc.view.suffix=.jsp
 *     注意：如果/WEB-INF/jsp/最后不加'/'，则在返回的字符串'ok'和'fail'的最前面加'/'
 * 3.如果要把返回的字符串映射成一个jsp文件，在类的注解上不能用@RestController，因为该注解中内部包含了@ResponseBody注解，
 *   还是用@Controller比较好
 *
 * 参考: https://blog.csdn.net/u010502101/article/details/78867730
 */
@Controller
public class IndexController {

    /**
     * 截取所有的根目录请求
     * @param model 返回参数 类似request
     * @return
     */
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("message", "com.lx.demo.springboot.controller.IndexController");
        return "index";
    }
}
