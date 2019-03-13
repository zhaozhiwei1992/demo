package com.lx.demo.springbootbstrap.controller;

import com.lx.demo.springbootbstrap.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    /**
     * 需要通过自动配置来初始化person
     */
    @Autowired Person person;

    @GetMapping("/person")
    public Person getPerson(){
        return person;
    }
}
