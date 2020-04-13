package com.lx.demo.j8.lambada4userinterface;

public class UserDTO {
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

    public UserDTO() {
    }

    public UserDTO(Long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public static UserDTO getUserByName(String name){
        UserDTO user = new UserDTO();
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
