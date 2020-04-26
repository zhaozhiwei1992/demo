package com.lx.demo.springbootcache.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Component;

/**
 * 其他方法接入上述注解后就会加入日志
 *  这种方式适合定点爆破, 谁用谁加， 事务注解应该也可以根据这种方式进行搞{@link Transactional}
 *
 *  可以通过配置profile，根据激活模式激活拦截器
 *  目前该拦截器只能启动dev模式下激活 -Dspring.profiles.active=dev
 */
@Aspect
@Component
public class CacheAnnotationAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAnnotationAspect.class);

    /**
     * 加入指定注解就会被拦截
     */
    @Pointcut("@annotation(cacheable)")
    public void pointcut(Cacheable cacheable) {
    }

    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    /**
     * 切点由上面方法指定，并且可以把参数传入到后续引用切点的面中
     * @param joinPoint
     * @param cacheable
     */
    @Before("pointcut(cacheable)")
    public void before(JoinPoint joinPoint, Cacheable cacheable) {
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        logger.info(String.format("当前请求: %s, 开始时间: %s, 我想说: %s", joinPoint.toString(), beginTime.get(), cacheable.value()));
    }

    @Autowired
    private SimpleCacheManager simpleCacheManager;

    /**
     * 这里优先级在缓存前，无法获取cache中信息
     * @param joinPoint
     * @param cacheable
     */
    @After("pointcut(cacheable)")
    public void after(JoinPoint joinPoint, Cacheable cacheable) {

        final String s = cacheable.cacheNames()[0];
        final Cache cache = simpleCacheManager.getCache(s);
        final Cache.ValueWrapper name = cache.get("name");
        logger.info("缓存信息{}", name);
        //统计耗时
        logger.info(String.format("当前请求: %s, 耗时: %s, 我想说: %s", joinPoint.toString(), System.currentTimeMillis()-beginTime.get(), cacheable.value()));
    }
}
