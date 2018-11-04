package com.lx.demo.j8.lambada4userinterface;

public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public static User getUserByName(String name){
        User user = new User();
        user.setName("ligoudan");
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
