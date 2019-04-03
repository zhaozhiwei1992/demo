package com.example.springbootactivity.domain;

import javax.persistence.*;

/**
 * @author zhaoz
 */
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    private int age;

    @OneToOne
    private Comp comp;

    public Person(){

    }

    public Person(String name) {
        this.name = name;
    }

    public Comp getComp() {
        return comp;
    }

    public void setComp(Comp comp) {
        this.comp = comp;
    }

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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
