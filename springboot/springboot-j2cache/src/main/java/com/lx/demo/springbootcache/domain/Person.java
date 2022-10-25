package com.lx.demo.springbootcache.domain;

import java.io.Serializable;

public class Person implements Serializable {

    private long serialVersionUID = 1;
    // field of a Serializable class is not declared 'private static final long'

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
