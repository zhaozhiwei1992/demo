package com.example.springbootaop.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanSelfAwareProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanSelfAware) {
//        该类被拦截了，所以一定会创建为代理类
            System.out.println("inject proxy：" + bean.getClass());
            BeanSelfAware myBean = (BeanSelfAware)bean;
            myBean.setSelf(bean);
            return myBean;
        }
        return bean;
    }
}