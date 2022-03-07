package com.example.web.resource;

import com.example.domain.User;
import com.example.exception.UserNotFoundException;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.domain.Error;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.web.controller
 * @Description: TODO
 * @date 2022/2/21 上午9:09
 */
@Controller
//该注解默认每个方法都会有@RequestBody和@ResponseBody
//@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * @Description: 内容协商处理
     * com.example.config.WebMvcConfig#users
     * ContentNegotiatingViewResolver
     * 测试:http://127.0.0.1:8080/users
     * {
     * <p>
     * "userList": [
     * {
     * "id": 1,
     * "name": "zhangsan",
     * "password": null,
     * "age": 0,
     * "createTime": null
     * },
     * {
     * "id": 2,
     * "name": "lisi",
     * "password": null,
     * "age": 0,
     * "createTime": null
     * }
     * ]
     * <p>
     * }
     * <p>
     * responsebody 返回的结果, 没有userList这个model key
     * [
     * <p>
     * {
     * "id": 1,
     * "name": "zhangsan",
     * "password": null,
     * "age": 0,
     * "createTime": null
     * },
     * {
     * "id": 2,
     * "name": "lisi",
     * "password": null,
     * "age": 0,
     * "createTime": null
     * }
     * <p>
     * ]
     */
    @GetMapping(value = "users",
//            表明只处理请求Accept为 application/json的, producer表示提供xx
            produces = "application/json"
    )
    @ResponseBody
    public List<User> users(Model model
            , @RequestParam(value = "startIndex", defaultValue = "1") int startIndex
            , @RequestParam(value = "count", defaultValue = "20") int count) {
        return userService.findUsers(startIndex, count);
    }

    /**
     * @Description: 浏览器network 返回的结果 404 Not Found
     * ResponseEntity已经包含了@ResponseBody的语义，不需要该注解
     * http://127.0.0.1:8080/user/1  ok
     * http://127.0.0.1:8080/user/9   not found
     */
    @GetMapping("user/{id}")
    public ResponseEntity<?> user(@PathVariable(value = "id") int id) {
        final User one = userService.findOne(id);
//        HttpStatus httpStatus = one == null? HttpStatus.NOT_FOUND: HttpStatus.OK;
        if (one == null) {
//            final Error error = new Error("404001", "User id [" + id + "] not found!");
//            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

//            抛出异常统一处理 com.example.exception.UserNotFoundExceptionHandler.userNotFound
            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<>(one, HttpStatus.OK);
    }

    // 改方式如果获取不到对象, 状态码还是200不友好
//    @GetMapping("user/{id}")
//    @ResponseBody
//    public User user(@PathVariable(value = "id") int id){
//        return userService.findOne(id);
//    }

    /**
     * curl -X POST http://127.0.0.1:8080/user -H "Content-Type:application/json;charset=UTF-8"
     * -H "Accept:application/json;charset=UTF-8" -d '{"id":1,"name":"zhngsan"}'
     * -i    该参数可以返回output header信息
     */
    @PostMapping(value = "user"
            , produces = "application/json"
//            客户端content-type需要匹配consumer
            , consumes = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> user(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        final User save = userService.save(user);
        final HttpHeaders httpHeaders = new HttpHeaders();
        URI location = uriComponentsBuilder
                .path("/user/")
                .path(String.valueOf(user.getId()))
                .build()
                .toUri();
        httpHeaders.setLocation(location);
        return new ResponseEntity<>(save, httpHeaders, HttpStatus.CREATED);
    }
}
