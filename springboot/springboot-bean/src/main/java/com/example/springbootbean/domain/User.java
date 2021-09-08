package com.example.springbootbean.domain;

import java.util.Date;

public class User {
    private long id;
    private String name;

    /**
     * spring 默認也沒有對該屬性進行實現， 所以直接獲取bean會報錯
     * Exception in thread "main" org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'user' defined in class path resource [context.xml]:
     * Initialization of bean failed; nested exception is org.springframework.beans.ConversionNotSupportedException:
     * Failed to convert property value of type 'java.lang.String' to required type 'java.util.Date' for property 'date';
     * nested exception is java.lang.IllegalStateException:
     * Cannot convert value of type 'java.lang.String' to required type 'java.util.Date' for property 'date': no matching editors or conversion strategy found
     */
    private Date date;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public User() {
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
