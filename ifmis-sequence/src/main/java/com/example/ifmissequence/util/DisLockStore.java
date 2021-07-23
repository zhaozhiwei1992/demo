/**
 * 
 */
package com.example.ifmissequence.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Title: LockStore
 * @Package com/example/ifmissequence/util/LockStore.java
 * @Description: 基于redis分布式加锁
 * @author zhaozhiwei
 * @date 2021/7/23 上午11:59
 * @version V1.0
 */
@Component
public final class DisLockStore {

    private static final String LOCK_PREFIX = "bdg_";

    private static final long LOCK_EXPIRE = 3;

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean getLock(String key) {
        String lockKey = LOCK_PREFIX + key;
        return (Boolean) redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = redisConnection.setNX(lockKey.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                // 获得锁
                return true;
            } else {
                byte[] value = redisConnection.get(lockKey.getBytes());
                if (Objects.nonNull(value) && value.length > 0) {
                    long expireTime = Long.parseLong(new String(value));
                    if (expireTime < System.currentTimeMillis()) {
                        // 如果锁已经过期
                        byte[] oldValue = redisConnection.getSet(lockKey.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        // 防止死锁
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * 根据锁名释放锁
     *
     * @param key
     *            锁名
     */
    public  void releaseLock(String key) {

    }
}
