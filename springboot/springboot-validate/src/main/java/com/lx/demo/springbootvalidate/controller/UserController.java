package com.lx.demo.springbootvalidate.controller;

import com.lx.demo.springbootvalidate.domain.User;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *  1. 如果某些属性的规则确定，适合用注解形式, 比如说not null, 或者age < 20等
 *  2. 如果属性在不同请求中校验不能相同，并且可能冲突， 那只能自定义或者使用groups, 比如a方法中age > 20 b方法中age < 20
 */
@RestController
public class UserController {

    /**
     * 传统校验方式需要自己做很多判断，如果pojo属性很多时候比较麻烦, 耦合度很高
     * @param user
     * @return
     */
    @GetMapping("/user/tradition")
    public User saveUserTradition(User user){
        if(StringUtils.isEmpty(user.getName())){
            System.out.println("用户名不能为空");
        }else{
           //...
        }
        return user;
    }

    /**
     * curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":1}' http://127.0.0.1:8080/user/save
     *
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public User saveUser(@Validated @RequestBody User user) {
        return user;
    }

    /**
     * ➜  ~ curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":99999999,"name":"lisi","email":"11@qq.com","phone":"18234xx2"}' http://127.0.0.1:8080/user/save
     * 这种情况下的校验输出会呗bing捕获， 可以在controller中处理异常
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/user/save/bindingresult")
    public User saveUser(@Validated @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                System.out.println(fieldError);
            }
            return null;
        } else {
            return user;
        }
    }

    /**
     * 能不能看片看年龄 >18 可以
     *
     * 加入分组后只会控制 SexMovie分组，其他校验全部失效
     * <p>
     * {"timestamp":"2018-11-14T07:10:58.177+0000","status":400,"error":"Bad Request","errors":[{"codes":["Min.user.age","Min.age","Min.int","Min"],"arguments":[{"codes":["user.age","age"],"arguments":null,"defaultMessage":"age","code":"age"},18],"defaultMessage":"未成年人不能看片","objectName":"user","field":"age","rejectedValue":1,"bindingFailure":false,"code":"Min"}],"message":"Validation failed for object='user'. Error count: 1","path":"/user/sexmovie"}%
     * ➜  ~ curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":1,"name":"lisi","email":"11@qq.com","phone":"18212356459","age":1}' http://127.0.0.1:8080/user/sexmovie
     *
     * @param user
     * @return
     */
    @PostMapping("/user/sexmovie")
    public User lookSexMovie(@Validated({User.SexMovie.class}) @RequestBody User user) {
        return user;
    }
}
