package com.example.web.rest;

import com.example.api.UserService;
import com.example.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
@RestController
@Slf4j
public class UserResource{

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * 保存用户
     * curl -X POST http://127.0.0.1:9090/users -H "Content-Type:application/json;charset=utf8" -d '{"id":66,"name":"zhangsan"}'
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
//            , ignoreExceptions = {NullPointerException.class}//空指针不触发fallback
    )
    @GetMapping("/users")
    public List<User> getAllUser() throws InterruptedException {

        int executeTime = random.nextInt(200);
        logger.info(String.format("Execute time %sms ", executeTime));
        Thread.sleep(executeTime);

        // 抛出空指针异常测试　打开忽略空指针异常后，就不会进入fallback
//        if(1>0){
//            throw new NullPointerException();
//        }
        return userService.getAllUser();
    }

    /**
     * 测试通过原生接口跳转, 为了保持原汁原味，不不改变userservie接口，该接口同时作为了feign客户端,强迫症
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Long id){
//        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(principal instanceof UserDetails){
//            UserDetails user = (UserDetails) principal;
//            final Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//            authorities.forEach(authoritie -> log.info("当前登录用户: {}, 角色为: {}", user.getUsername(), authoritie.getAuthority()));
//        }
        return userService.findById(id);
    }

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
    /**
     * 超时回调
     * @return
     */
    public List<User> fallbackTimeout(Throwable throwable) {
        logger.error("访问超时!");
//        logger.error("访问超时! 异常信息:{}", throwable);
        return Collections.emptyList();
    }
}
