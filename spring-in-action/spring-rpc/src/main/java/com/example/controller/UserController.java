package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.controller
 * @Description: TODO
 * @date 2022/3/6 下午6:10
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/find/1")
    public void findOne(){
        final User user = userService.findOne(1);
        System.out.println(user);
    }
}
