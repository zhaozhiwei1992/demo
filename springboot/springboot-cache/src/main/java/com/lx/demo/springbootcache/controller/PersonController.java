package com.lx.demo.springbootcache.controller;

import com.lx.demo.springbootcache.domain.Person;
import com.lx.demo.springbootcache.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SimpleCacheManager simpleCacheManager;

    @GetMapping("/person/get/{id}")
    public Person findByID(@PathVariable Long id){
        final Collection<String> cacheNames = simpleCacheManager.getCacheNames();
        System.out.println("所有的缓存信息" + cacheNames);
        final Person byID = personRepository.findByID(id);

//        这里的cache是ConcurrentMapCache, 默认的key是方法参数值
        // 记录到缓存后，这里可以拿到值
        Cache cache = simpleCacheManager.getCache("persons");
        System.out.println("缓存值: " + cache.get(id).get());

        return byID;
    }

    @GetMapping("/person/get2/{id}")
    public Person findByID2(@PathVariable Long id){
        return personRepository.findByID2(id);
    }
}
