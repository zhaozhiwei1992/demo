package com.lx.demo.domain;


import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author zhaoz
 * {@see https://mp.baomidou.com/guide/annotation.html#tablename}
 */
@TableName("t_user")
public class User {

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