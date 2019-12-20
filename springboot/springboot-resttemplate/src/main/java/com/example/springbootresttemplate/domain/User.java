package com.example.springbootresttemplate.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User {
    private Long id;
    private String name;
    private int age;
}
