package com.example.springbootsecurityjwt.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootsecurityjwt.domain.Authority;
import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootsecurityjwt.web.controller
 * @Description: TODO
 * @date 2021/1/3 下午11:14
 */
@RestController
public class LoginController {

    /**
     * 方法名：作用：登陆校验密码
     * 输入 username password  用户名，密码
     * 输出：code: 状态码   1 为认证成功 0 为用户不存在 -1 为密码不一致 -2 表示程序错误
     *       success:  true or false 执行成功或失败
     *       result：只在认证成功时返回，包含用户的全部信息
     *       messsage:
     *
      curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0.1:8080/login
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String toLogin(User user) {
        JSONObject json=new JSONObject();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String encode = bCryptPasswordEncoder.encode("11");
        try {
            User user1 = new User();
            user1.setPassword(encode);
            String userName = "admin";
            user.setAuthorities(new HashSet(Arrays.asList(new Authority("ADMIN1"), new Authority("USER"))));
            user.setId(0L);
            user.setName(userName);
            user.setAge(0);
            user.setPassword("11");
            user.setActivated(true);

            if (user1!=null) {
                String dbPassWord = user1.getPassword();
                if (bCryptPasswordEncoder.matches(user.getPassword(),dbPassWord)) {
                    //创建token

                    String token = JwtUtil.generateToken(user.getName());
                    json.put("success", true);
                    json.put("code", 1);
                    //json.put("result", user1);
                    json.put("time", new Date().toString());
                    json.put("message", "登陆成功");
                    json.put(JwtUtil.AUTHORIZATION,token);
                } else {
                    String token = JwtUtil.generateToken(user.getName());
                    json.put(JwtUtil.AUTHORIZATION,token);
                    json.put("success", false);
                    json.put("code", -1);
                    json.put("message", "登陆失败,密码错误");
                }
            }else {
                json.put("success", false);
                json.put("code", 0);
                json.put("message", "无此用户信息");
            }
        } catch (Exception e) {
            json.put("code", -2);
            json.put("success", false);
            json.put("message", e.getMessage());

        }

        return JSON.toJSONString(json);
    }
}
