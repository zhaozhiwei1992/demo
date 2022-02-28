package com.example.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/21 上午9:08
 */
@Entity
@Table(name="t_user")
public class User {

    @Id
    private long id;

    private String name;

    private String password;

    private int age;

    private Date createTime;

    @Version
    private int version;

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", version=" + version +
                '}';
    }
}
