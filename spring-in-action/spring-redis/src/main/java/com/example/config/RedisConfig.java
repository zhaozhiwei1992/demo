package com.example.config;

import com.example.domain.User;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/3/2 上午9:13
 */
@Configuration
@EnableRedisRepositories(basePackages = "com.example.repository")
// 使用缓存必须启用这个.
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
//        jedisConnectionFactory.setDatabase();
//        jedisConnectionFactory.setPassword();
        jedisConnectionFactory.setDatabase(0);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        final RedisTemplate<String, User> objectObjectRedisTemplate = new RedisTemplate<>();
        objectObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        objectObjectRedisTemplate.setKeySerializer(new StringRedisSerializer());
        objectObjectRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        return objectObjectRedisTemplate;
    }

//    @Bean
//    public CacheManager simpleCacheManager(){
//        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("cache-1");
//        ConcurrentMapCache concurrentMapCache2 = new ConcurrentMapCache("persons");
//        simpleCacheManager.setCaches(Arrays.asList(concurrentMapCache, concurrentMapCache2));
//        return simpleCacheManager;
//    }

//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate){
//        final RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
//        return redisCacheManager;
//    }

    /**
     * @data: 2022/3/4-上午10:36
     * @User: zhaozhiwei
     * @method: cacheManager
      * @param redisTemplate :
     * @return: org.springframework.cache.CacheManager
     * @Description: 同时启用多个缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){

        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("cache-1");
        ConcurrentMapCache concurrentMapCache2 = new ConcurrentMapCache("users");
        simpleCacheManager.setCaches(Arrays.asList(concurrentMapCache, concurrentMapCache2));

        final RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);

//        同时启用多个管理器
        final CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        final List<CacheManager> cacheManagers = Arrays.asList(
                simpleCacheManager,
                redisCacheManager
        );
        compositeCacheManager.setCacheManagers(cacheManagers);
        return compositeCacheManager;
    }

}
