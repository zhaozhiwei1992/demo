package com.example.springbootproperties.controller;

import com.example.springbootproperties.config.TableConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    @Autowired
    private TableConfiguration personConfiguration;

    @GetMapping("/echo/person")
    public String echo(){
        System.out.println(personConfiguration.getTables());
        return personConfiguration.getTables().toString();
    }
}
