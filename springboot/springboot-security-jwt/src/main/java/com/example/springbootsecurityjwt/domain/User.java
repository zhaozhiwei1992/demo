package com.example.springbootsecurityjwt.domain;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private int age;
    private String password;
    private boolean activated = false;

    public boolean isActivated() {
        return activated;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    private Set<Authority> authorities = new HashSet<>();

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActivated() {
        return activated;
    }



    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
