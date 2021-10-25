package com.example.springbootlocks.web.controller;

import com.example.springbootlocks.locks.SingleInsLockStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootlocks.web.controller
 * @Description: TODO
 * @date 2021/10/25 下午2:12
 */
@RestController
public class SingleLockStoreController {

    @GetMapping("/lock")
    public String lock() {
        final Boolean lock = SingleInsLockStore.getLock("com.example.springbootlocks.web.controller" +
                ".SingleLockStoreController");
        if (lock) {
            return "success";
        } else {
            return "fail";
        }
    }

    @GetMapping("/release")
    public String releaseLock() {
        SingleInsLockStore.releaseLock("com.example.springbootlocks.web.controller.SingleLockStoreController");
        return "success";
    }
}
