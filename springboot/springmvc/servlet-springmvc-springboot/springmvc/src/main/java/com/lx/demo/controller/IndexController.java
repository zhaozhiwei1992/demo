package com.lx.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author 赵志伟
 * @ClassName: UserController
 * @description [描述该类的功能]
 * @create 2018-05-12 上午7:09
 **/
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 截取所有的根目录请求
     * @param model 返回参数 类似request
     * @return
     */
    @RequestMapping(value = "/")
    public String index(Model model){
        model.addAttribute("message", "com.lx.demo.controller.IndexController");
        return "index";
    }

    @ResponseBody
    @RequestMapping("/hello/{name}/{msg}")
    public String hello(@PathVariable("name") String name, @PathVariable("msg") String msg){
        String result = "hello " + name + " " + msg;
        logger.info(result);
        return result;
    }

//    @ResponseBody
    @RequestMapping("/hello")
    public String helloParam(@RequestParam("name") String name, @RequestParam("msg") String msg){
        String result = "hello " + name + " " + msg;
        logger.info(result);
        return "index";
    }
}
