package com.example.springbootatomikos.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: User
 * @Package com/example/springbootatomikos/domain/User.java
 * @Description:
 * create table user (
 *   id   bigint       not null,
 *   age  integer      not null,
 *   name varchar(255) not null,
 *   primary key (id)
 * ) engine = InnoDB
 *
 * @author zhaozhiwei
 * @date 2022/4/25 上午11:37
 * @version V1.0
 */
@Entity
public class User {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    private int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
