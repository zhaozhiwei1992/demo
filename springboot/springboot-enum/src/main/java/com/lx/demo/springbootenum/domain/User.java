package com.lx.demo.springbootenum.domain;

import com.lx.demo.springbootenum.contrant.Gender;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private int age;
    private Gender gender;
}
