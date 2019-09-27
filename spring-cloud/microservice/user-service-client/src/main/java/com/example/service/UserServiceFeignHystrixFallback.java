package com.example.service;

import com.example.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class UserServiceFeignHystrixFallback implements UserServiceFeignHystrix{
    @Override
    public List<User> getAllUser() {
        log.info("time out ..");
        return Collections.emptyList();
    }
}
