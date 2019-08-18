package com.lx.demo.springbootshiro.domain;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private long id;
    private int age;
    private String name;
    private String loginName;
    private String password;
    private String salt;//加密密码的盐
    private List<Role> roleList;// 一个用户具有多个角色

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.loginName+this.salt;
    }
}
