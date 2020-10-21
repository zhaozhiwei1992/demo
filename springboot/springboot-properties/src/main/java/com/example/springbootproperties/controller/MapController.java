package com.example.springbootproperties.controller;

import com.example.springbootproperties.config.PersonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MapController {

    @Autowired
    private PersonConfiguration personConfiguration;

    @GetMapping("/echo/person")
    public String echo(){
        System.out.println(personConfiguration.getTables());
        return personConfiguration.getTables().toString();
    }
}
