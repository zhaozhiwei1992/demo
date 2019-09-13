package com.lx.demo;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

@DemoAnnotation(value = "hello")
public class DemoAnnotationDemo {

    /**
     * java是如何处理注解的
     *
     * 个人认为可以看做是加载类或者方法上的aop
     *
     * @com.lx.demo.DemoAnnotation(value=hello)
     * {@link AnnotationInvocationHandler}
     *
     * 通过debug，　发现demoAnnotation是一个代理对象
     *
     * 通过反射修改注解属性值
     * @param args
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        DemoAnnotation demoAnnotation = DemoAnnotationDemo.class.getAnnotation(DemoAnnotation.class);
        System.out.println(demoAnnotation.value());

        //获取 demoAnnotation 这个代理实例所持有的 InvocationHandler
        InvocationHandler h = Proxy.getInvocationHandler(demoAnnotation);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) hField.get(h);
        // 修改 value 属性值
        memberValues.put("value", "ddd");
        // 获取 demoAnnotation 的 value 属性值
        System.out.println(demoAnnotation.value()); // ddd
    }
}
