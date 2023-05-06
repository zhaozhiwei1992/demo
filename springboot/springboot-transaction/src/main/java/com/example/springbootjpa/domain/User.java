package com.example.springbootjpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_user")
@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 5482161605591549178L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String password;

    private int age;
}
