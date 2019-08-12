package com.lx.demo.springbootmongodbmulti.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private long id;
    private String name;
    private int age;
}
