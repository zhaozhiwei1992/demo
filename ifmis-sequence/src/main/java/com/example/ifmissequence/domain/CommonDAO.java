package com.example.ifmissequence.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.domain
 * @Description: 模拟数据库，for update
 * @date 2021/7/23 下午10:45
 */
@Component
public class CommonDAO {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加缓存
     * @param key 键
     * @return true成功 false失败
     */
    public boolean release(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean forUpdate(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在, 存在则还在forupdate状态
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
