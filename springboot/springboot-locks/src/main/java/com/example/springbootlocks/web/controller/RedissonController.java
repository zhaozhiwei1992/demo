package com.example.springbootlocks.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RedissonController {

    private int max_int = 0;

    @Autowired
    private RedissonClient redisson;

    @GetMapping("redissonLock")
    public String getLock(){
        log.info("进入方法");
        RLock rLock = redisson.getLock("redissonLock");

        try {
            // 30秒自动减锁
            rLock.lock(30, TimeUnit.SECONDS);
            log.info("进入了锁");
            log.info("max_int: {}", max_int++);
            Thread.sleep(1000);
            // 手动解锁
            rLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "redissonLock";
    }
}