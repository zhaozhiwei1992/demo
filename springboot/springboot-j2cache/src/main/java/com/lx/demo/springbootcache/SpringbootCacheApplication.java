package com.lx.demo.springbootcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Title: SpringbootCacheApplication
 * @Package com/lx/demo/springbootcache/SpringbootCacheApplication.java
 * @Description:
 * 1. 简单缓存测试 com.lx.demo.springbootcache.config.CacheConfig#simpleCacheManager()
 * 2. ehcache测试
 * 3. 使用j2cache实现二级缓存 内存 + redis
 * 保证增删改缓存的准确性
 * @author zhaozhiwei
 * @date 2022/9/23 上午9:54
 * @version V1.0
 */
@SpringBootApplication
@EnableCaching
public class SpringbootCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCacheApplication.class, args);
	}

}

