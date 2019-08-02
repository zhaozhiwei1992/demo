package com.example.service;

import com.example.api.UserService;
import com.example.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户服务， 数据存储在内存中
 */
@Service
public class InMemeryUserService implements UserService {

    private Map<Long, User> repository = new ConcurrentHashMap<>();

    @Override
    public boolean createUser(User user) {
        return repository.put(user.getId(), user) == null;
    }

    @Override
    public List<User> getAllUser() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public User findById(Long id) {
        return repository.get(id);
    }
}
