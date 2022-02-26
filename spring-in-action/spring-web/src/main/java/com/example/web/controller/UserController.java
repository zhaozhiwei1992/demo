package com.example.web.controller;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.web.controller
 * @Description: TODO
 * @date 2022/2/21 上午9:09
 */
@Controller
public class UserController {

    private UserRepository userRepository;

//    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @data: 2022/2/21-下午3:15
     * @User: zhaozhiwei
     * @method: users
      * @param model :
     * @return: java.util.List<com.example.domain.User>
     * @Description:
     *
     * 逻辑视图的名称将会根据请求路径推断得出。因为这个方法处理针
     * 对“/users”的GET请求，因此视图的名称将会是users
     *
     * http://127.0.0.1:8080/spring-web/users
     */
    @GetMapping("users")
    public List<User> users(Model model
            , @RequestParam(value = "startIndex", defaultValue = "1") int startIndex
            , @RequestParam(value = "count", defaultValue = "20") int count){
        final List<User> users = userRepository.findUsers(startIndex, count);
//        List<User>，因此，键将会推断为userList
//        model.addAllAttributes(users);
//        model.addAllAttributes("userList", users);
        return users;
//        return "users";
    }

    @GetMapping("user/{id}")
    public String user(Model model
            , @PathVariable(value = "id") int id){

        if(!model.containsAttribute("user")){
            final User user = userRepository.findOne(id);
            model.addAttribute(user);
        }
        return "user";
    }
}
