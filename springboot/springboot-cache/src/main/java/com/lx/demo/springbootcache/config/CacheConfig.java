package com.lx.demo.springbootcache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 自定义cachemap
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 自定义方式会覆盖原有实现cachemanager接口的管理器，可用缓存容器中必须包含{@see PersonRepository}中cacheable#names中要使用的容器
     * @return
     */
    @Bean
    public SimpleCacheManager simpleCacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("cache-1");
        ConcurrentMapCache concurrentMapCache2 = new ConcurrentMapCache("persons");
        simpleCacheManager.setCaches(Arrays.asList(concurrentMapCache, concurrentMapCache2));
        return simpleCacheManager;
    }
}
