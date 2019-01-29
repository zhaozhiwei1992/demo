package com.lx.demo.springbootvalidate.controller;

import com.lx.demo.springbootvalidate.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @See LocalValidatorFactoryBean
 */
@RestController
public class ValidatorController {
    
    @Autowired
    Validator globalValidator;

    /**
     * 手动校验
     * 参考: https://blog.csdn.net/u013815546/article/details/77248003
     * @return
     */
    @GetMapping("/validate")
    public String validate() {
        User user = new User();

        Set<ConstraintViolation<User>> set = globalValidator.validate(user);
        for (ConstraintViolation<User> constraintViolation : set) {
            System.out.println("spring默认实现validator输出信息: " + constraintViolation.getMessage());
        }

        return "success";
    }

    /**
     * @See https://phoenixnap.com/kb/spring-boot-validation-for-rest-services
     * @return
     */
    @GetMapping("/validatebyjava")
    public String validatebyjava() {
        User user = new User();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext().getValidator();
        Set<ConstraintViolation<User>> set = validator.validate(user);
        for (ConstraintViolation<User> constraintViolation : set) {
            System.out.println("使用原生javaapi输出信息: " + constraintViolation.getMessage());
        }
        return "success";
    }
}
