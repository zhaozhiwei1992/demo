package com.example.domain;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description:
 * 通过aop不改变代码情况下动态引入jump动作, 类似javascript, python之类的动态方法
 * 目标:
 * 为所有Run接口的实现引入Jump接口
 * @date 2022/2/20 上午12:00
 */
public interface Jump {

    void jump();
}
