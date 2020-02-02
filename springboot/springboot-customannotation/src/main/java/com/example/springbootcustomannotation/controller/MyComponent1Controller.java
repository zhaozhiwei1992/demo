package com.example.springbootcustomannotation.controller;

import com.example.springbootcustomannotation.service.MyComponent1Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyComponent1Controller {

    @Autowired
    private MyComponent1Bean myComponent1Bean;

    @GetMapping("/echo/{info}")
    public String echo(@PathVariable String info){
        return myComponent1Bean.echo(info);
    }
}
