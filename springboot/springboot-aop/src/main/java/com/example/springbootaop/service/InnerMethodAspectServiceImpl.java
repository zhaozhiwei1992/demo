package com.example.springbootaop.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: InnerMethodAspect
 * @Package com/example/springbootaop/aop/InnerMethodAspect.java
 * @Description:
 * 嵌套service拦截的一种实现思路
 * https://www.iteye.com/blog/fyting-109236
 * @date 2021/9/22 下午2:58
 */
@Service
public class InnerMethodAspectServiceImpl implements InnerMethodAspectService, BeanSelfAware {

    //AOP增强后的代理对象
    private InnerMethodAspectService self;

    public void setSelf(Object proxyBean) {
        this.self = (InnerMethodAspectService) proxyBean;
    }

    public void someMethod() {
        //方法1: 配合 @EnableAspectJAutoProxy(exposeProxy = true)使用
        ((InnerMethodAspectService)AopContext.currentProxy()).someInnerMethod();

        //方法2: 直接注入proxy对象, 注意这句，通过self这个对象，而不是直接调用的
//        self.someInnerMethod();
    }

    public void someInnerMethod() {
    }
}