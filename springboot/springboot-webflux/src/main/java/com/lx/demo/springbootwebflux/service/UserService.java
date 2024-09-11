package com.lx.demo.springbootwebflux.service;

import com.lx.demo.springbootwebflux.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    /**
     * 用户仓储
     */
    private final Map<Long, User> userRepository = new ConcurrentHashMap<Long, User>();

    /**
     * id生成器
     */
    private final AtomicLong idGenerator = new AtomicLong();


    public User save(User user){
        final long id = idGenerator.incrementAndGet();
        user.setId(id);
        userRepository.putIfAbsent(user.getId(), user);
        return user;
    }

    public List<User> findAll(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(userRepository.values());
    }

    public User findById(Long id) {
        return userRepository.get(id);
    }

    public User update(User user) {
        return userRepository.put(user.getId(), user);
    }

    public void delete(Long id) {
        userRepository.remove(id);
    }
}
