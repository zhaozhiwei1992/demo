package com.lx.demo.springbootcache.repository;

import com.lx.demo.springbootcache.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {
    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    private Person person2 = new Person();

    /**
     * 打开缓存后， 后续查询都会直接从缓存中取， 需要enablecache
     * 使用spring注解的方式缓存可以管理， 设置超时事件
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "persons")
    public Person findByID(Long id){
        logger.info("方法内部查询!");
        Person person = new Person();
        person.setId(0L);
        person.setName("张三");

        person2.setId(0L);
        person2.setName("张三");
        return person;
    }

    /**
     * 通过全局变量保存缓存信息
     * @param id
     * @return
     */
    public Person findByID2(Long id){
        return person2;
    }
}
