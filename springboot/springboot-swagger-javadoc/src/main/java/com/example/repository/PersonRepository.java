package com.example.repository;

import com.example.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private static final Logger log = LoggerFactory.getLogger(PersonRepository.class);

    public static final List<Person> personList = new ArrayList();

    public Person findByID(Long id){
        return personList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(new Person());
    }

    public Person save(Person person) {
        person.setId(Long.parseLong(String.valueOf(personList.size()+1)));
        personList.add(person);
        return person;
    }

    public Person deleteByID(Long id) {
        final Person byID = this.findByID(id);
        personList.removeIf(next -> next.getId().equals(id));
        return byID;
    }

    public Person update(Person person, Long id) {
        this.deleteByID(id);
        personList.add(person);
        return person;
    }
}
