package com.example.springbootsession.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.example.springbootsession.domain.User]
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8722135893925031622L;
    private Long id;
    private String name;
    private int age;
}
