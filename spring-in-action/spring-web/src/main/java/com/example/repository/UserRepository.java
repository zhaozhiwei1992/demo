package com.example.repository;

import com.example.domain.User;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/2/21 上午11:05
 */
public interface UserRepository {

    List<User> findUsers(int startIndex, int Count);
}
