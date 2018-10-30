package com.lx.demo.j8.lambada4userinterface;

public class UserDao {
    public User savd(IUser user){
        System.out.println("save ...");
        return user.getUserByName("李四");
    }
}
