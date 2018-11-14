package com.lx.demo.springbootvalidate.domain;

import javax.validation.constraints.*;

/**
 * bean后端校验测试
 * <p>
 * 分组校验
 * 未满18不可以看片
 * //单元测试不知道怎么搞
 */
public class User {


    public interface SexMovie {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Min(value = 18, groups = {SexMovie.class}, message = "未成年人不能看片")
    private int age;

    @Max(value = 1000)
    private long id;

    @NotNull
    private String name;

    /**
     * 自定义校验规则
     * 处理一些正则表达式也处理不了的规则
     */
    @Card
    private String cardNum;

    /**
     * 允许为空，有值时候校验合法
     */
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Pattern(regexp = "^1[387][0-9]{9}", message = "手机号格式不正确")
    private String phone;

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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardNum='" + cardNum + '\'' +
                '}';
    }
}
