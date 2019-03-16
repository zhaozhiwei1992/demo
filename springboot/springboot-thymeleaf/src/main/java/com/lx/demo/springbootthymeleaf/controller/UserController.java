package com.lx.demo.springbootthymeleaf.controller;

import com.lx.demo.springbootthymeleaf.domain.User;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 要使用thymeleaf必须引入 thymeleaf的包
 * application.properties中使用user前缀要小心，spring本身也有个user
 */
@Controller
@EnableConfigurationProperties(User.class)
public class UserController {

    private User user;
    public UserController(User user) {
        this.user = user;
    }

    @GetMapping("/")
    public String index(ModelMap model) {
        model.addAttribute("name", user.getName());
        model.addAttribute("msg", user.toString());
        return "index";
    }
}
