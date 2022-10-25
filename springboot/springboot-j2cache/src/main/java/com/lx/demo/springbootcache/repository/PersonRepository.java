package com.lx.demo.springbootcache.repository;

import com.lx.demo.springbootcache.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class PersonRepository {

    private static final Logger log = LoggerFactory.getLogger(PersonRepository.class);

    public static final List<Person> personList = new ArrayList();

    @Cacheable(cacheNames = "person",key = "'detail'+#id")
    public Person findByID(Long id){
        log.info("没有走缓存, 获取用户: {}", id);
        return personList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(new Person());
    }

    public Person save(Person person) {
        person.setId(Long.parseLong(String.valueOf(personList.size()+1)));
        personList.add(person);
        return person;
    }

    @CacheEvict(value = "person", key = "'detail'+#id")
    public Person deleteByID(Long id) {
        final Person byID = this.findByID(id);
        personList.removeIf(next -> next.getId().equals(id));
        return byID;
    }

    @CachePut(value = "person", key = "'detail'+#id")
    public Person update(Person person, Long id) {
        this.deleteByID(id);
        personList.add(person);
        return person;
    }
}
