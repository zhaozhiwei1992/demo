package com.lx.demo.springbootcache.controller;

import com.lx.demo.springbootcache.domain.Person;
import com.lx.demo.springbootcache.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/person/get/{id}")
    public Person findByID(Long id){
        return personRepository.findByID(id);
    }

    @GetMapping("/person/get2/{id}")
    public Person findByID2(Long id){
        return personRepository.findByID2(id);
    }
}
