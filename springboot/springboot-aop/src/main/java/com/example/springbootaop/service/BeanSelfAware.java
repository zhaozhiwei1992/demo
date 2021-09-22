package com.example.springbootaop.service;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootaop.service
 * @Description: 实现该接口表示将自己的代理对象也设置给pojo对象
 * @date 2021/9/22 下午3:01
 */
public interface BeanSelfAware {

    void setSelf(Object proxyBean);
}
