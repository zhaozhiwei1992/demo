package com.lx.demo.j8.lambada4userinterface;

public class User {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private int age;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public User() {
    }

    public User(Long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public static User getUserByName(String name){
        User user = new User();
        user.setName("ligoudan");
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
