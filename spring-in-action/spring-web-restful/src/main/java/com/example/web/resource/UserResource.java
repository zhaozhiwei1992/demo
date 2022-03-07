package com.example.web.resource;

import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * @Description: 内容协商处理
     * com.example.config.WebMvcConfig#users
     * ContentNegotiatingViewResolver
     * 测试:http://127.0.0.1:8080/users
     * {
     *
     *     "userList": [
     *         {
     *             "id": 1,
     *             "name": "zhangsan",
     *             "password": null,
     *             "age": 0,
     *             "createTime": null
     *         },
     *         {
     *             "id": 2,
     *             "name": "lisi",
     *             "password": null,
     *             "age": 0,
     *             "createTime": null
     *         }
     *     ]
     *
     * }
     */
    @GetMapping("users")
    public List<User> users(Model model
            , @RequestParam(value = "startIndex", defaultValue = "1") int startIndex
            , @RequestParam(value = "count", defaultValue = "20") int count){
        return userService.findUsers(startIndex, count);
    }

    @GetMapping("user/{id}")
    public String user(Model model
            , @PathVariable(value = "id") int id){

        if(!model.containsAttribute("user")){
            final User user = userService.findOne(id);
            model.addAttribute(user);
        }
        return "user";
    }
}
