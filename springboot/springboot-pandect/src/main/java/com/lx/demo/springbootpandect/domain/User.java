package com.lx.demo.springbootpandect.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@ToString
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 4402188576690174703L;
    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int age;
}
