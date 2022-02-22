package com.example.web.controller;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.web.controller
 * @Description: TODO
 * @date 2022/2/22 上午11:26
 */
@Controller
public class LoginController {

    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(){
        return "registerForm";
    }

    @PostMapping("/register")
    public String processRegistration(User user){
        userRepository.save(user);
        return "redirect:/user/"+user.getId();
    }
}
