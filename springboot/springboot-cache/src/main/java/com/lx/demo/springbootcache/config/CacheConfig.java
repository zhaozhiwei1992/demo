package com.lx.demo.springbootcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 自定义cachemap
 * cachemanager一般配合cacheable注解使用
 * 如果用redistemplate操作redis, 跟这个没关系
 */
@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    /**
     * 自定义方式会覆盖原有实现cachemanager接口的管理器，可用缓存容器中必须包含{@see PersonRepository}
     * 中cacheable#names中要使用的容器
     * {@see https://stackoverflow.com/questions/27968157/expiry-time-cacheable-spring-boot}
     * @return
     */
    @Bean
    public CacheManager simpleCacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("cache-1");
        ConcurrentMapCache concurrentMapCache2 = new ConcurrentMapCache("persons");
        simpleCacheManager.setCaches(Arrays.asList(concurrentMapCache, concurrentMapCache2));
        return simpleCacheManager;
    }

    /**
     * @data: 2021/9/21-下午11:57
     * @User: zhaozhiwei
     * @method: simpleCacheEvict

     * @return: void
     * @Description:
     * 清理本身不支持expire的缓存实现
     * 时间可自己制定
     */
    @CacheEvict(allEntries = true, value = {"persons", "cache-1"})
    @Scheduled(fixedDelay = 6 * 1000 ,  initialDelay = 500)
    public void simpleCacheEvict() {
        System.out.println("Flush Cache " + new SimpleDateFormat().format(new Date()));
    }

    //todo 同时假如各种缓存 redis, jcache, ehcache等，如何切换
    // spring-in-action/spring-redis
}
