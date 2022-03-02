package com.example.repository;

import com.example.domain.User;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description:
 * 当Spring Data JPA为Repository接口生成实现的时候，它还会查找名字
 * 与接口相同，并且添加了Impl后缀的一个类。如果这个类存在的
 * 话，Spring Data JPA将会把它的方法与Spring Data JPA所生成的方法合
 * 并在一起
 * @date 2022/3/2 下午6:06
 */
public class UserRepositoryImpl implements UserRepositoryExt{

    @Override
    public User findUserExt() {
        System.out.println("扩展成功");
        return null;
    }
}
