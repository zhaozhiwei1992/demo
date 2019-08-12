package com.lx.demo.springbootmongodb.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = -6008414266262510979L;
    private long id;
    private String name;
    private int age;
}
