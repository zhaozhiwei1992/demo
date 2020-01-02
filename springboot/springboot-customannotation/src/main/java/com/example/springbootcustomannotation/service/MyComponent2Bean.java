package com.example.springbootcustomannotation.service;


import com.example.springbootcustomannotation.annotation.CustomizeComponent2;

@CustomizeComponent2
public class MyComponent2Bean {

    public String echo(String info){
        return "echo: " + info;
    }
}
