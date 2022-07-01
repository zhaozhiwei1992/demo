package com.lx.demo.springbootcache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

    /**
     * @data: 2021/9/22-上午12:08
     * @User: zhaozhiwei
     * @method: cacheData
      * @param key :
 * @param value :
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Description: 描述
     *curl -X POST http://127.0.0.1:8888/cache/save\?key\=1\&value\=xx
     */
    @PostMapping(value = "/save")
    public Map<String, Object> cacheData(@RequestParam String key, @RequestParam String value) {
        Cache cache = simpleCacheManager.getCache("cache-1");
        cache.put(key, value);

        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);

        // map2.putAll(map);
        return map;
    }

    /**
     * @data: 2021/9/22-上午12:09
     * @User: zhaozhiwei
     * @method: findByKey
      * @param key :
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Description: 描述
     * curl -X GET http://127.0.0.1:8888/cache/find/1
     */
    @GetMapping(value = "/find/{key}")
    public Map<String, Object> findByKey(@PathVariable String key) {

        final Collection<String> cacheNames = simpleCacheManager.getCacheNames();
        System.out.println("所有的缓存信息" + cacheNames);

        Cache cache = simpleCacheManager.getCache("cache-1");
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, cache.get(key).get());

        // System.out.println(map2);
        return map;
    }
}
