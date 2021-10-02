package com.lx.demo.springbootjdbc;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * @Title: MapperPropertyTestDemo
 * @Package com/lx/demo/springbootjdbc/MapperPropertyTestDemo.java
 * @Description:
 * 测试不同属性复制性能差异, 代理请求时间统计
 * @author zhaozhiwei
 * @date 2021/10/2 下午10:04
 * @version V1.0
 */
public class RequestTimeCostProxy implements MethodInterceptor {

    public Object getInstence(Class<?> clazz){

        Enhancer enhancer = new Enhancer();
        //要把哪个设置为即将生成的新类父类
        enhancer.setSuperclass(clazz);

        enhancer.setCallback(this);

        return  enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final Object invoke = methodProxy.invokeSuper(o, objects);
        stopWatch.stop();
        System.out.println(method.getName() + " 请求次数: " + objects[objects.length-1] + " cost: " + stopWatch.getTotalTimeMillis());

        return invoke;
    }
}
