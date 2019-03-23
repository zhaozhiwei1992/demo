package com.lx.demo.springbootcache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用自定义的缓存管理器， 保存数据后从缓存中读取
 */
@RestController
@RequestMapping(value = "/cache")
public class CacheController {

    @Autowired
    private SimpleCacheManager simpleCacheManager;

    // private Map map2 = new HashMap();

    @PostMapping(value = "/save")
    public Map<String, Object> cacheData(@RequestParam String key, @RequestParam String value) {
        Cache cache = simpleCacheManager.getCache("cache-1");
        cache.put(key, value);

        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);

        // map2.putAll(map);
        return map;
    }

    @GetMapping(value = "/find/{key}")
    public Map<String, Object> findByKey(@PathVariable String key) {
        Cache cache = simpleCacheManager.getCache("cache-1");
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, cache.get(key).get());

        // System.out.println(map2);
        return map;
    }
}
