package com.example.service;

import com.example.domain.User;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/6 下午4:01
 */
public interface UserService {

    List<User> findUsers(int startIndex, int count);

    User findOne(int id);

    User save(User user);
}
