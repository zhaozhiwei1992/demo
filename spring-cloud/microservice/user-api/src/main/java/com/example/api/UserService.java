package com.example.api;

import com.example.domain.User;

import java.util.List;

/**
 * 对外提供服务的标准接口
 */
public interface UserService {

    boolean saveUser(User user);

    List<User> findAll();
}
