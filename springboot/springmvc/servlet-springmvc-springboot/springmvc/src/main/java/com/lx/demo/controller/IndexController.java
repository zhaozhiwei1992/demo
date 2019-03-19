package com.lx.demo.controller;

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
}
