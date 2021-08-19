package com.example.springbootdruid.service;

import com.example.springbootdruid.domain.User;
import com.example.springbootdruid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootdruid.service
 * @Description: 通过xml配置增加事务
 * @date 2021/8/19 下午4:29
 */
//@Transactional
//@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        final int save = userRepository.save(user);
        if (save > 0) {
//            throw new RuntimeException("测试异常");
            return user;
        }
        return user;
    }

}
