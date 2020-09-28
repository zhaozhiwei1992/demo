package com.example.springbootproperties.external;

import com.example.springbootproperties.controller.EchoController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 定义一个类实现BeanPostProcessor接口，在接口中进行反射操作。BeanPostProcessor是Spring提供的一个Bean扩展接口，可以通过该接口实现一些Bean创建之前和创建之后的操作。
 * 如果跟EchoControllerModifyListener同时存在，结果一listener为准
 * 作者：Sanisy
 * 链接：https://www.jianshu.com/p/70ee32b0b1eb
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Component
public class LocalBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EchoController) {
            try {
                Field field = EchoController.class.getDeclaredField("msg");
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, "updateBy_com.example.springbootproperties.external.LocalBeanProcessor");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}