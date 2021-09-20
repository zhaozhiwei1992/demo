package com.lx.demo.springbootsecurity.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * {@see demo/springboot-cache}
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public SimpleCacheManager simpleCacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("cache-1");
        ConcurrentMapCache concurrentMapCache2 = new ConcurrentMapCache("bus.cloud.security.authUserCache");
        simpleCacheManager.setCaches(Arrays.asList(concurrentMapCache, concurrentMapCache2));
        return simpleCacheManager;
    }

    //todo 同时假如各种缓存 redis, jcache, ehcache等，如何切换
}
