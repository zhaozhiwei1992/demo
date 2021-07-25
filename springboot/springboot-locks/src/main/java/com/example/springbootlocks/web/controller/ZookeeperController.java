package com.example.springbootlocks.web.controller;

import com.example.springbootlocks.locks.ZookeeperLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ZookeeperController {

    private int max_int = 0;

    @GetMapping("zookeeperLock")
    public String getLock() {
        log.info("进入方法");
        try(ZookeeperLock zookeeperLock=new ZookeeperLock()) {
            boolean lock = zookeeperLock.getLock("zookeeper");
            if(lock){
                log.info("进入了锁");
                log.info("max_int: {}", max_int++);
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "zookeeperLock";
    }
}