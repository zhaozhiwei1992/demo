package com.example.springbootdruid.controller;

import com.example.springbootdruid.domain.User;
import com.example.springbootdruid.service.TransactionService;
import com.example.springbootdruid.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootdruid.controller
 * @Description: 测试事务
 * @date 2021/8/19 下午4:28
 */
@RestController
@RequestMapping("/trac")
public class TransactionController {

    @Resource(name = "transactionServiceTx")
    private TransactionService transactionService;

    @PostMapping("user")
    public User save(@RequestParam String name){
//        transactionService = (TransactionService) SpringUtil.getBean("transactionServiceTx");
        User user = new User();
        user.setName(name);
        transactionService.save(user);
        return user;
    }
}
