package com.lx.demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: MethodGenericTypeGetDemo
 * @Package com/lx/demo/MethodGenericTypeGetDemo.java
 * @Description: 只有一个泛型类 获取泛型类型, 对比GenericTypeGetDemo获取子类类型
 * @author zhaozhiwei
 * @date 2022/9/8 上午12:02
 * @version V1.0
 */
public class MethodGenericTypeGetDemo {
    public static void main(String[] args) {
        // 要啥给啥, 我要String, 就返回给我String
        // 注意如果只定义一个泛型类, 必须是abstract否则是无法获取类型的
        // 或者就是定义子类来指定类型, https://blog.csdn.net/changsa65/article/details/78790881
        final InnerGenericTypeClass<String> innerClass = new InnerGenericTypeClass<String>(){};

        // 在类的外部这样获取
        Type type = ((ParameterizedType)innerClass.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(type);

        // 在类的内部这样获取
        System.out.println(innerClass.getTClass());

        final List<String> method = innerClass.method();

        // 获取实际返回数据类型
        System.out.println(method);
        final Class<?> aClass = method.get(0).getClass();
        System.out.println("通过数据获取类型" + aClass);


    }

}

/**
 * @Title: InnerGenericTypeClass
 * @Package com/lx/demo/MethodGenericTypeGetDemo.java
 * @Description: 注意这里如果只有一个泛型类, 必须定义abstract, 否则getTClass方法无法获取泛型类型
 * @author zhaozhiwei
 * @date 2022/9/8 上午12:00
 * @version V1.0
 */
abstract class InnerGenericTypeClass<T>{
    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
    public List<T> method(){
        // 获取泛型的类型, 根据类型定义返回不同的数据
        final Class<T> tClass = getTClass();
        //TODO 通过反射获取不同类型，创建不同类型list 返回数据
        final List<String> list = new ArrayList<>();
        list.add("hh");
        return (List<T>) list;
    }
}
