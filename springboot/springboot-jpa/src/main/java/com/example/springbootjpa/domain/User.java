package com.example.springbootjpa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 这里实体是否有get，set方法很重要， 如果没get， controller解析json时候就不会加入这个属性
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 5482161605591549178L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String password;

    private int age;

//    @OneToOne
//    private Book book;

//    @OneToMany(mappedBy = "user")
//    private Collection<Book> books;

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @ManyToMany
    private Collection<Book> books;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
