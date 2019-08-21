package com.lx.demo.springbootwebflux.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private int age;
}
