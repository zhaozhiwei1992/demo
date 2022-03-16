package com.example.springbootaop.external;

import com.example.springbootaop.controller.LogController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @Title: LocalBeanProcessor
 * @Package com/example/springbootaop/external/LocalBeanProcessor.java
 * @Description:
 * 测试 给cglib代理后的对象属性赋值, 需要在before里覆盖属性即可
 * @author zhaozhiwei
 * @date 2022/3/16 下午5:20
 * @version V1.0
 */
@Component
public class LocalBeanProcessor implements BeanPostProcessor {

    /**
     * @Description: 这个方法设置的cglib代理后仍然生效
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        这里拿到的是cglib前的对象
        if (bean instanceof LogController) {
            try {
                Field field = LogController.class.getDeclaredField("msg");
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, "updateBy_com.example.springbootaop.external.LocalBeanProcessor.before");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        这里拿到的是cglib后的对象
        if (bean instanceof LogController) {
            try {
                Field field = LogController.class.getDeclaredField("msg");
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, "updateBy_com.example.springbootaop.external.LocalBeanProcessor.after");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}