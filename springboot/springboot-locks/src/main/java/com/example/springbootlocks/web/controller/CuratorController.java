package com.example.springbootlocks.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class CuratorController {

    private int max_int = 0;

    @GetMapping("curatorLock")
    public String getLock() {
        log.info("进入了方法");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        client.start();
        InterProcessMutex lock=new InterProcessMutex(client,"/order");
        try {
            // 加锁等待最大阻塞时间
            if(lock.acquire(30, TimeUnit.SECONDS)){
                try {
                    log.info("进入了锁");
                    log.info("max_int: {}", max_int++);
                    Thread.sleep(1000);
                }finally {
                    lock.release();
                    log.info("释放了锁");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "curatorLock";
    }
}