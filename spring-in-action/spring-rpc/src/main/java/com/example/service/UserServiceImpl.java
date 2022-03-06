package com.example.service;

import com.example.domain.User;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/4 下午5:20
 */
public class UserServiceImpl implements UserService{

    public List<User> findUsers(int startIndex, int count){
        return null;
    }

    public User findOne(int id){
        System.out.println("请求到方法 findOne");
        return new User(1, "zhangsan");
    }

    public User save(User user){
        return null;
    }
}
