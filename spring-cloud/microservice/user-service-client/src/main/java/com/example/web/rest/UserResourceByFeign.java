package com.example.web.rest;

import com.example.api.UserService;
import com.example.domain.User;
import com.example.service.UserServiceFeignHystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务客户端，真正跟外界对接的服务
 * 通过feigh的方式来调用服务
 *
 * 这里不推荐实现userservice接口的方式，实现接口可以继承接口的注解
 */
@RestController
public class UserResourceByFeign {

    /**
     * 通过api的方式直接引入 provider提供的客户端接口包
     * 跟第三方对接使用该方式也会更加优雅
     */
//    @Autowired
    private UserServiceFeignHystrix userServiceFeignHystrix;

    @GetMapping("/users/feign")
    List<User> getAllUserByFeign(){
        return userServiceFeignHystrix.getAllUser();
    }

    /**
     * 这个userservice被feign初始化，必须打开enablefeign
     * 推荐直接autowried
     * 也可以通过实际对象全完整路径获取到feign实例
     */
    @Autowired
    @Qualifier("com.example.api.UserService")
    private UserService userService;

    /**
     * curl -X POST http://127.0.0.1:8080/users -H "Content-Type:application/json;charset=utf8" -d '{"id":998,"name":"zhangsan"}'
     * 这里会使用feign的方式请求服务端
     * @param user
     * @return
     */
    @PostMapping("/users")
    boolean createUser(@RequestBody  User user){
        return userService.createUser(user);
    }

    @GetMapping("/users")
    List<User> getAllUser(){
        return userService.getAllUser();
    }

    /**
     * 测试聚合服务，　目前绕不过去provider的security
     * curl -X GET http://localhost:10086/aggregate/998
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Long id){
        final User user = new User();
        user.setId(id);
        user.setName("测试聚合");
        user.setAge(998);
        return user;
    }

//    @Autowired
//    private UserServiceFeignContract userServiceFeignContract;
//
//    @GetMapping("/id")
//    User findById(@PathVariable("id") Long id){
//        return  userServiceFeignContract.findById(id);
//    }
}

