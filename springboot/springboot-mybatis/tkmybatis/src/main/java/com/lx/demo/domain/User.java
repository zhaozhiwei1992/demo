package com.lx.demo.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhaoz
 */
@Table(name = "t_user")
public class User {

    /**
     *
     // @Id表示该字段对应数据库表的主键id
     // @GeneratedValue中strategy表示使用数据库自带的主键生成策略.
     // @GeneratedValue中generator配置为"JDBC",在数据插入完毕之后,会自动将主键id填充到实体类中.类似普通mapper.xml中配置的selectKey标签
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
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
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}