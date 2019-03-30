package com.example.springcloudhystrixserviceprovider.web.controller;

import com.example.springcloudhystrixserviceprovider.aop.UserHystrixCommand;
import com.example.springcloudhystrixserviceprovider.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 使用传统方式拦截异常
 * 当方法执行时间超过 100 ms 时，触发异常
 *
 * {@see com.netflix.hystrix.HystrixCommandProperties}
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static Random random = new Random();

    @GetMapping("/user/hystrix/list")
    public List<User> getUsersByHystrix() throws TimeoutException, InterruptedException {
        long executeTime = random.nextInt(200);
        // 通过休眠来模拟执行时间
        System.out.println("Execute Time : " + executeTime + " ms");
        Thread.sleep(executeTime);

        return new UserHystrixCommand("").execute();
    }


    @HystrixCommand(
            commandProperties = {
                    // 设置操作时间为 100 毫秒
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            },
            fallbackMethod = "faultToleranceTimeout"
    )
    @GetMapping("/user/list")
    public List<User> getUsers() throws TimeoutException, InterruptedException {
        long executeTime = random.nextInt(200);
        // 通过休眠来模拟执行时间
        System.out.println("Execute Time : " + executeTime + " ms");
        Thread.sleep(executeTime);

        return Arrays.asList(new User(1L, "张三", 18), new User(2L, "李四", 19));
    }

    /**
     * getUser的超时后的返回方法，这里返回值签名一定要与getuser一致
     * 否则会报下面异常
     * --> interface java.util.List<Ooops!>
     * Command type literal pos: 1; Fallback type literal pos: 1
     * @return
     */
    public List<User> faultToleranceTimeout() {
        logger.error("execute timeout 100ms");
        return Collections.emptyList();
    }
}
