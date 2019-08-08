package com.example.springbootjpa.domain;

import com.example.springbootjpa.domain.listener.CustomListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * EntityListener可以指定监听器，监听数据持久化a
 */
@Entity
@EntityListeners(CustomListener.class)
public class Book implements Serializable {
    private static final long serialVersionUID = 230883684204974285L;

    @Id
//    @GeneratedValue
    //会自动生成一个id生成器, 不指定默认生成 hibernate_sequence的表
//    @GeneratedValue(generator = "book_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float price;

//    @OneToOne(mappedBy = "book")
//    @ManyToOne
//    private User user;

    @ManyToMany(mappedBy = "books")
    private Collection<User> users;

    public Book(Long id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Book() {
    }

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
