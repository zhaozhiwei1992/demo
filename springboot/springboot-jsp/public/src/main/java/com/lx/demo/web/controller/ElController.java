package com.lx.demo.web.controller;

import com.lx.demo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: ElController
 * @Package com/lx/demo/web/controller/ElController.java
 * @Description: TODO 大佬写点东西
 * @date 2020/12/23 上午11:19
 * @see https://www.cnblogs.com/Qian123/p/5308951.html
 */
@Controller
@RequestMapping("/public")
public class ElController {

    /**
     * https://www.cnblogs.com/zhaolei1996/p/10700795.html
     * Servlet.service() for servlet [jsp] in context with path [] threw exception [java.lang.NullPointerException]
     * with root cause
     *
     * @param m
     * @return
     */
    @GetMapping("/el")
    public String hello(Model m) {
        m.addAttribute("date", DateFormat.getDateTimeInstance().format(new Date()));
        m.addAttribute("username", "ligoudan");

//      前台显示用户信息
        List<User> list = new ArrayList<>();
        list.add(new User(1, "张飒飒", 25));
        list.add(new User(2, "李四四", 26));
        list.add(new User(3, "王五五", 23));
        list.add(new User(4, "赵六六", 24));
        // 需要一个Model对象
        m.addAttribute("users", list);

        return "el/el";
    }
}
