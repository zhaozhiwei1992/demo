package com.lx.demo.springbootshiro.domain;

import lombok.Data;

@Data
public class User {
    private long id;
    private int age;
    private String name;
    private String loginName;
    private String password;
}
