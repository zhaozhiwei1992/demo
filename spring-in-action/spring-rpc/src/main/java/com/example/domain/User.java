package com.example.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description:
 * 远程接口传输对象，序列化反序列化需要实现Serializable接口
 * @date 2022/2/21 上午9:08
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4772967803492966828L;

    private long id;

    private String name;

    private String password;

    private int age;

    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public User(long id, String name, String password, int age, Date createTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.createTime = createTime;
    }

    public User(long id, Date createTime) {
        this.id = id;
        this.createTime = createTime;
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that,"id", "name");
    }

    @Override
    public int hashCode() {
//        return Objects.hash(id, name);
        return HashCodeBuilder.reflectionHashCode(this, "id", name);
    }
}
