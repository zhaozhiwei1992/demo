package com.lx.demo.springbootpandect.web.controller;

import com.lx.demo.springbootpandect.domain.User;
import com.lx.demo.springbootpandect.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private Environment environment;

    /**
     * yaml形式
     */
    @Value("${my.name}")
    private String name;

    @GetMapping("/users")
    public User initUserByProperties(){
        log.info("@Value获取my.name属性: " + name);
        User user = new User();
        user.setId(Long.valueOf(environment.getProperty("my.id")));
        user.setName(name);
        user.setAge(Integer.parseInt(environment.getProperty("my.age")));
        return user;
    }

    @Autowired
    private UserRepository userRepository;

    /**
     * curl -X DELETE http://localhost:8080/users/test
     * 只能删除4位用户名
     * @param name
     */
    @DeleteMapping("/users/{name:[a-z]{4}}")
    public void deleteUser(@PathVariable String name){
        userRepository.findByName(name).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Delete User: {}", user);
        });
    }

    /**
     * 更新操作
     * @param user
     * @return
     */
    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return this.userRepository.saveAndFlush(user);
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable("id") Long id){
        return userRepository.findById(id).get();
    }

    /**
     * curl -X POST -H Content-Type:application/json -d {"id":1, "name":"lisi", "age":18} http://localhost:8080/users
     * {"id":1,"name":"lisi","age":18}
     * 实体属性还是需要加requestbody的
     * @param user
     * @return
     */
    @PostMapping("/users")
    public User save(@RequestBody User user){
        return userRepository.save(user);
    }

    /**
     * 如果通过pathvariable方式，这种情况下不能自动初始化
     * @param name
     * @return
     */
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable("name") String name){
        return Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + name;
    }

    /**
     * spring自动注入属性
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(String name){
        return Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + name;
    }

}
