package com.example.springbootsession.web.rest;

import com.example.springbootsession.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserResource {

    /**
     *
     * com.example.springbootsession.web.rest.UserResource#findUserById: User(id=1, name=ligoudan, age=18)
     * 查询后加入到redis中
     * 查询key  keys user*
     * redis中key cachename[user]::类名#方法名[]
     *  StringBuilder sb = new StringBuilder();
     *             sb.append(target.getClass().getName());
     *             sb.append("#");
     *             sb.append(method.getName());
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    @Cacheable(value = "user")
    public User findUserById(Long id){
        final User user = new User();
        user.setId(id);
        user.setName("ligoudan");
        user.setAge(18);
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        log.info(element.getClassName() + "#" + element.getMethodName() + ": {}", user);
        return user;
    }
}
