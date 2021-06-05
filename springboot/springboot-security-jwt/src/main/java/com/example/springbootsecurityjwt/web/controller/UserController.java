package com.example.springbootsecurityjwt.web.controller;

import com.example.springbootsecurityjwt.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootsecurityjwt.web.controller
 * @Description: 登录获取token后测试token效果
 * @date 2021/1/3 下午10:46
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        logger.info("登录成功");
        return "success";
    }

    /**
     * @data: 2021/1/4-下午5:25
     * @User: zhaozhiwei
     * @method: user
     * @return: java.lang.String
     * @Description: 带着tokenid请求，认证通过即可
     * curl -H "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9
     * .eyJ1c2VyTmFtZSI6ImFkbWluIiwiaWF0IjoxNjA5NzUxNzUwLCJleHAiOjE2MDk4MzgxNTB9
     * .x1wrpaSQcyklXmsImlGgTsRuRMk75fSzHgBe7CiLhAY" http://127.0.0.1:8080/user/all
     */
    @GetMapping("/all")
    public String user() {

        // 可通过request,或者securitycontext获取请求信息, securitycontext信息需呀自己封装到detail中
        logger.info("当前用户tokenid: {}", SecurityUtil.getTokenId());
        return "查询成功";
    }
}
