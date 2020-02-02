package com.example.springbootcustomannotation.service;

import com.example.springbootcustomannotation.annotation.CustomizeCompenent1;

@CustomizeCompenent1
public class MyComponent1Bean {

    public String echo(String info){
        return "echo: " + info;
    }
}
