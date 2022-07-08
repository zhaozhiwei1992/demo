package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Title: UserService
 * @Package com/lx/demo/springbootcommandlinerunner/service/UserService.java
 * @Description: spring容器加载完成后做一些不可描述的事
 * @author zhaozhiwei
 * @date 2021/8/21 下午5:35
 * @version V1.0
 */
@Service
public class UserService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
        logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
    }

    /**
     * @data: 2021/8/21-下午5:34
     * @User: zhaozhiwei
     * @method: postConstruct

     * @return: void
     * @Description:
     *     @PostConstruct；在具体Bean的实例化过程中执行，@PostConstruct注解的方法，会在构造方法之后执行；
     *
     * 　　　　加载顺序为：Constructor > @Autowired > @PostConstruct > 静态方法；
     *
     * 　　　　特点：
     *
     *         只有一个非静态方法能使用此注解
     *         被注解的方法不得有任何参数
     *         被注解的方法返回值必须为void
     *         被注解方法不得抛出已检查异常
     *         此方法只会被执行一次
     */
    @PostConstruct
    public void postConstruct(){
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
        logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
        logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
    }
}
