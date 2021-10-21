package com.example.springboottest.repository;

import com.example.springboottest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springboottest.repository
 * @Description: TODO
 * @date 2021/10/20 下午3:08
 */
@Component
public class UserRepository {

    @Autowired
    private User user;

    public User findOne(){
        return user;
    }
}
