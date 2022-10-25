package com.lx.demo.springbootcache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;

/**
 * 自定义cachemap
 * cachemanager一般配合cacheable注解使用
 * 如果用redistemplate操作redis, 跟这个没关系
 */
//@Configuration
//@EnableCaching
public class CacheConfig {

    /**
     * 自定义方式会覆盖原有实现cachemanager接口的管理器，可用缓存容器中必须包含{@see PersonRepository}
     * 中cacheable#names中要使用的容器
     * {@see https://stackoverflow.com/questions/27968157/expiry-time-cacheable-spring-boot}
     * @return
     */
//    @Bean
//    public CacheManager simpleCacheManager(){
//        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("cache-1");
//        ConcurrentMapCache concurrentMapCache2 = new ConcurrentMapCache("persons");
//        simpleCacheManager.setCaches(Arrays.asList(concurrentMapCache, concurrentMapCache2));
//        return simpleCacheManager;
//    }

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * @data: 2022/10/25-上午10:21
     * @User: zhaozhiwei
     * @method: cacheManager

     * @return: org.springframework.cache.CacheManager
     * @Description: redis缓存
     */
    @Bean
    public CacheManager cacheManager() {
        // 设置缓存有效期1小时
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer((new StringRedisSerializer())))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((new GenericJackson2JsonRedisSerializer())))
                .computePrefixWith((name)->name+":");
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
