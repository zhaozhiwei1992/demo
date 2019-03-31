package com.example.fallback;

import com.example.api.UserService;
import com.example.domain.User;

import java.util.Collections;
import java.util.List;

/**
 * 请求发生错误时的兜底方法
 */
public class UserServiceFallback implements UserService {
    @Override
    public boolean createUser(User user) {
        return false;
    }

    @Override
    public List<User> getAllUser() {
        return Collections.emptyList();
    }
}
