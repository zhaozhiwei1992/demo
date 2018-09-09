package com.lx.demo.junit.service;

import com.lx.demo.junit.domain.User;

import java.util.List;

/**
 * 第三方引入的service， 本地没有实现, 也无法访问远程实现
 */
public interface UserRemoteService {

    /**
     * 返回所有User
     * @return
     */
    public List<User> findAll();
}
