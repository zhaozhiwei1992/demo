package com.example.web.controller;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String showRegistrationForm(Model model){
        model.addAttribute(new User(1, "ttang"));
        return "registerForm";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid User user, Errors errors){
        if(errors.hasErrors()){
//            如果有异常会直接返回到当前页面, 并且填充错误信息
            System.out.println("报错了");
            return "registerForm";
        }
        userRepository.save(user);
        return "redirect:/user/"+user.getId();
    }
}
