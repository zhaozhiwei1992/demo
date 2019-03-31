package com.example.web.rest;

import com.example.api.UserService;
import com.example.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
@RestController
public class UserResource{

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/users")
    public boolean createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    private static final Random random = new Random();
    /**
     * 查询所有用户
     *
     * 这里加点逻辑， 模拟超时被hystrix拦截
     * @return
     */
    @HystrixCommand(
            commandProperties = @HystrixProperty(
                    name="execution.isolation.thread.timeoutInMilliseconds", value = "100"
            ),
            fallbackMethod = "fallbackTimeout"
    )
    @GetMapping("/users")
    public List<User> getAllUser() throws InterruptedException {

        int executeTime = random.nextInt(200);
        logger.info(String.format("Execute time %sms ", executeTime));
        Thread.sleep(executeTime);
        return userService.getAllUser();
    }

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
    /**
     * 超时回调
     * @return
     */
    public List<User> fallbackTimeout() {
        logger.error("访问超时!");
        return Collections.emptyList();
    }
}
