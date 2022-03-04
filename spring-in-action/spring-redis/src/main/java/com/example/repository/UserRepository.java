package com.example.repository;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save() {
        final User zhangsan = new User(1, "zhangsan");
        redisTemplate.opsForValue().set(zhangsan.getName(), zhangsan);
    }

    public void remove(){
        redisTemplate.opsForValue().set("zhangsan", null);
        final User zhangsan = redisTemplate.opsForValue().get("zhangsan");
    }
}
