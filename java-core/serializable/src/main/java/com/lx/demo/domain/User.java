package com.lx.demo.domain;

import java.io.Serializable;


/**
 * 序列化对象
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2083262352962630020L;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
