package com.lx.demo.aop;

import com.lx.demo.datasourcerouting.MasterSlaverDataSource;
import com.lx.demo.annotation.DS;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * https://blog.csdn.net/neosmith/article/details/61202084
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(com.lx.demo.annotation.DS)")
    public void beforeSwitchDS(JoinPoint point) {

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = MasterSlaverDataSource.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        MasterSlaverDataSource.setDB(dataSource);
    }


    /**
     * Caused by: java.lang.IllegalArgumentException: error Type referred to is not an annotation type: DS
     * @param point
     */
    @After("@annotation(com.lx.demo.annotation.DS)")
    public void afterSwitchDS(JoinPoint point) {
        MasterSlaverDataSource.clearDB();
    }
}
