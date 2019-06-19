package com.example.springbootsession.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class SessionController {

    /**
     * 参考: https://blog.csdn.net/chenliaoyuanjv/article/details/79689028
     * 1. 通过浏览器多次请求测试，自动带入cookie没有问题，每次都是同一个
     * 2. 通过curl，模拟浏览器请求方式, 带上cookie否则每次都用新的
     *  curl -c "mycookie" -X GET http://127.0.0.1:8080/uid   //保留cookie
     *  curl -b "mycookie" -X GET http://127.0.0.1:8080/uid   //带着cookie请求
     * redis-cli中输入 keys '*sessions*'
     * @param session
     * @return
     */
    @GetMapping("/uid")
    public String getSessionInfo(HttpSession session){
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
