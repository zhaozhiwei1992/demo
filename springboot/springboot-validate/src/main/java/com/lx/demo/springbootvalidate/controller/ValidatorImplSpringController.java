package com.lx.demo.springbootvalidate.controller;

import com.lx.demo.springbootvalidate.domain.User;
import com.lx.demo.springbootvalidate.validator.spring.UserValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Spring defines its own interface for validation Validator (org.springframework.validation.Validator).
 * It can be set for specific DataBinder instance and implements validation without annotations (not declarative approach).
 * @See  https://phoenixnap.com/kb/spring-boot-validation-for-rest-services
 */
@RestController
public class ValidatorImplSpringController {

    @Autowired
    private UserValidatorImpl userValidator;

    /**
     * Add Validator implementation to DataBinder:
     * @param binder
     */
    @InitBinder("user")
    public void initBinder(WebDataBinder binder){
        binder.addValidators(userValidator);
    }

    @PostMapping("/user/validateimplspringvalidator")
    public User saveUserValidateBySpring(@Valid @RequestBody User user){
         return user;
    }
}
