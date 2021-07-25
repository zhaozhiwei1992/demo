package com.example.springbootlocks.web.controller;

import com.example.springbootlocks.locks.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RedisController {

    private int max_int = 0;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("redisLock")
    public String getLock() {
        String redisKey = "rediskey";
        log.info("进入了方法");

        // 没有延时等待，获取锁失败就跳过
        try (RedisLock redisLock = new RedisLock(redisTemplate, redisKey, 30)) {
            boolean lock = redisLock.getLock();
            if(lock){
                log.info("进入了锁");
                log.info("max_int: {}", max_int++);
                Thread.sleep(1500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "redislock";
    }
}