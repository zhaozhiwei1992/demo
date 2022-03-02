package com.example.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * @Title: User
 * @Package com/example/domain/User.java
 * @Description:
 * @Document跟jpa中@Entity类似
 * @author zhaozhiwei
 * @date 2022/3/2 下午2:16
 * @version V1.0
 */
@Document
public class Order {

    @Id
    private long id;

    @Field("client")
    private String customer;

    private String type;

    private Collection<Item> items = new LinkedHashSet<>();

//    @Version
    private int version;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Order(long id, String customer, String type) {
        this.id = id;
        this.customer = customer;
        this.type = type;
    }

    public Order(long id, String customer) {
        this.id = id;
        this.customer = customer;
    }

    public Order() {
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that,"id", "name");
    }

    @Override
    public int hashCode() {
//        return Objects.hash(id, name);
        return HashCodeBuilder.reflectionHashCode(this, "id", customer);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + customer + '\'' +
                ", password='" + type + '\'' +
                ", version=" + version +
                '}';
    }
}
