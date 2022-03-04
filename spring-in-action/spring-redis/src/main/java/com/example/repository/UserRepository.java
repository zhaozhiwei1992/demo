package com.example.repository;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description:
 * @date 2022/3/4 上午10:43
 */
@Repository
public class UserRepository {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Cacheable("userCache")
    public User findByName(String name) {
        System.out.println("调用了findByName方法");
        return redisTemplate.opsForValue().get(name);
    }

    /**
     * @Description: 手动指定缓存的key为用户名
     */
    @CachePut(value = "userCache", key = "#result.name")
    public User save(User user) {
        redisTemplate.opsForValue().set(user.getName(), user);
        return user;
    }

    @CacheEvict(value = "userCache")
    public void remove(String name){
        redisTemplate.opsForValue().set(name, null);
        final User zhangsan = redisTemplate.opsForValue().get(name);
    }
}
