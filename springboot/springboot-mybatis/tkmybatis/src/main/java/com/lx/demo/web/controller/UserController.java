package com.lx.demo.web.controller;

import com.lx.demo.domain.User;
import com.lx.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * curl http://127.0.0.1:8080/user/1
 *
 * 返回结果： {"id":1,"name":"admin","password":"21232f297a57a5a743894a0e4a801fc3","realname":"helloworld",
 * "avatar":null,"mobile":"13073804251","sex":"男","status":1,"createTime":"2017-09-04T21:32:15.000+0000"}%
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public User selectOne(@PathVariable int id){
        final User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
