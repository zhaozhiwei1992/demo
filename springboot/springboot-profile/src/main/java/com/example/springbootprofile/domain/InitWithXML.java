package com.example.springbootprofile.domain;

/**
 * 通过springxml方式初始化bean， 并且引入到springboot
 * https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#using-boot-importing-xml-configuration
 */
public class InitWithXML {
    private Long id;
    private String name;
    private int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "InitWithXML{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
