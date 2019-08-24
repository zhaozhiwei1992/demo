package com.example.springbootaop.aop;

import com.example.springbootaop.annotation.CustomLogAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 用于增强{@link com.example.springbootaop.annotation.CustomLogAnnotation}
 * 其他方法接入上述注解后就会加入日志
 *  这种方式适合定点爆破, 谁用谁加， 事务注解应该也可以根据这种方式进行搞{@link Transactional}
 *
 *  可以通过配置profile，根据激活模式激活拦截器
 *  目前该拦截器只能启动dev模式下激活 -Dspring.profiles.active=dev
 */
@Aspect
@Component
@Profile(value = "dev")
public class CustomLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(CustomLogAspect.class);

    /**
     * 加入指定注解就会被拦截
     * @param customLogAnnotation
     */
    @Pointcut("@annotation(customLogAnnotation)")
    public void pointcut(CustomLogAnnotation customLogAnnotation) {
    }

    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    /**
     * 切点由上面方法指定，并且可以把参数传入到后续引用切点的面中
     * @param joinPoint
     * @param customLogAnnotation
     */
    @Before("pointcut(customLogAnnotation)")
    public void before(JoinPoint joinPoint, CustomLogAnnotation customLogAnnotation) {
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        logger.info(String.format("当前请求: %s, 开始时间: %s, 我想说: %s", joinPoint.toString(), beginTime.get(), customLogAnnotation.value()));
    }

    @After("pointcut(customLogAnnotation)")
    public void after(JoinPoint joinPoint, CustomLogAnnotation customLogAnnotation) {

        //统计耗时
        logger.info(String.format("当前请求: %s, 耗时: %s, 我想说: %s", joinPoint.toString(), System.currentTimeMillis()-beginTime.get(), customLogAnnotation.value()));
    }
}
