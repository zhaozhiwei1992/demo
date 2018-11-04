package com.lx.demo.j8.lambada4userinterface;

/**
 * 如果要使用 IUser :: getUserByName语法， 该接口只能又一个未实现方法
 */
public interface IUser {
    User getUserByName(String name);
//    Boolean deleteUserByID(long id);
}
