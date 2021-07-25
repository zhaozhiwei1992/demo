/**
 * 
 */
package com.example.ifmissequence.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Title: LockStore
 * @Package com/example/ifmissequence/util/LockStore.java
 * @Description: 基于redis分布式加锁
 * @author zhaozhiwei
 * @date 2021/7/23 上午11:59
 * @version V1.0
 */
public final class DisLockStoreRedis implements AutoCloseable{

    private static final Logger logger = LoggerFactory.getLogger(DisLockStoreRedis.class);

    private RedisTemplate redisTemplate;
    private String key;
    private String value;
    //单位：秒
    private int expireTime;

    public DisLockStoreRedis(RedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.value = UUID.randomUUID().toString();
        this.expireTime = expireTime;
    }

    /**
     * 获取分布式锁
     *
     * @return
     */
    public boolean getLock() {
        RedisCallback<Boolean> redisCallback = connection -> {
            // 设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            // 设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            // 序列化key
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            // 序列化value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            // 执行setnx操作
            Boolean result = connection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };

        // 只有得到锁才返回，否则一直去获取锁, 阻塞for update
        while (true){
            Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
            if(lock){
                return lock;
            }else{
                try {
                    Thread.sleep(100 * RANDOM.nextInt(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final Random RANDOM = new Random();

    /**
     * 释放分布式锁
     *
     * @return
     */
    public boolean unLock() {
        // LUA脚本
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Arrays.asList(key);

        Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, value);
        logger.info("释放锁结果：" + result);

        return result;
    }

    @Override
    public void close() throws Exception {
        unLock();
    }
}