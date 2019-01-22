package com.example.springbootprofile.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 如果 spring.profiles.active=dev 走这个bean
 * 并且读取application-dev.properties这个配置文件
 */
@Service
@Profile(value = "dev")
public class DevProfileService implements ProfileService{
    @Override
    public String getProfileConfig() {
        return "I am dev";
    }
}
