package com.example.springbootcustomannotation.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Interface to be implemented by objects used within a BeanFactory which are themselves factories.
 * If a bean implements this interface, it is used as a factory for an object to expose, not directly as a bean*
 * instance that will be exposed itself
 * <p>
 * 普通的JavaBean是直接使用类的实例，但是如果一个Bean继承了这个借口，就可以通过getObject()方法来自定义实例的内容，在FactoryBeanTest的getObject()就通过代理了原始类的方法，自定义类的方法。
 * <p>
 * 作者：wcong
 * 链接：https://www.jianshu.com/p/7c2948f64b1c
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * @param <T>
 */
public class FactoryBeanTest<T> implements InitializingBean, FactoryBean<T> {
    private String innerClassName;

    public void setInnerClassName(String innerClassName) {
        this.innerClassName = innerClassName;
    }

    @Override
    public T getObject() throws Exception {
        Class innerClass = Class.forName(innerClassName);
        if (innerClass.isInterface()) {
            return (T) InterfaceProxy.newInstance(innerClass);
        } else {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(innerClass);
            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
            enhancer.setCallback(new MethodInterceptorImpl());
            return (T) enhancer.create();
        }
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return Class.forName(innerClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}