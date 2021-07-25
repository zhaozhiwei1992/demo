package com.example.springbootlocks.locks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RedisLock implements AutoCloseable {
    private RedisTemplate redisTemplate;
    private String key;
    private String value;
    //单位：秒
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.value = UUID.randomUUID().toString();
        this.expireTime = expireTime;
    }

    /**
     * 获取分布式锁
     * 如果需要一直获取，增加while循环
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

        Boolean lock = (Boolean) redisTemplate.execute(redisCallback);

//        一直不停重试锁
//        while(true){
//            if(lock){
//                return lock;
//            }
//        }

        return lock;
    }

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
        RedisScript<Boolean> redisScript=RedisScript.of(script,Boolean.class);
        List<String> keys = Arrays.asList(key);

        Boolean result =(Boolean) redisTemplate.execute(redisScript, keys, value);
        log.info("释放锁结果："+result);

        return result;
    }

    @Override
    public void close() throws Exception {
        unLock();
    }
}