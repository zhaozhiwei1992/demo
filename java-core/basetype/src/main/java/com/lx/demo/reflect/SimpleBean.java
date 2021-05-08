package com.lx.demo.reflect;

/**
 * @Title: SimpleBean
 * @Package com/lx/demo/reflect/SimpleBean.java
 * @Description: java各种反射性能测试
 * 注意定义要严格遵守JavaBean规范，否则在使用和反射相关工具时会出现NoSuchMethodException异常，或者导致性能非常差，JavaBean规范中最重要的几点如下：
 *
 * 1.类必须是public, 拥有public无参构造器,这样能够通过反射newInstance()动态构建对象.
 *          String className = ...;
 *          Class beanClass = Class.forName(className);
 *          Object beanInstance = beanClass.newInstance();
 * 2.因为反射newInstance使用的是无参构造器, 所以对象实例化和配置是分开的
 * 3.每一个property都有一个public的getter和setter方法, 命名方式是get/set+首字母大写的property名
 *
 * @author zhaozhiwei
 * @date 2021/5/8 下午2:25
 * @version V1.0
 */
public class SimpleBean {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}