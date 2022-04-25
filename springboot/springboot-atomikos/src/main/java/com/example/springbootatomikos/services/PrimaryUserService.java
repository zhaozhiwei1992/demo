package com.example.springbootatomikos.services;

import com.example.springbootatomikos.domain.User;
import com.example.springbootatomikos.repository.primary.PrimaryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootatomikos.services
 * @Description:
 *
 * 分布式事务，同时写入两个数据库,并保证事务统一
 *
 * 如果是两个业务, 可能连接各自数据库，是否仍然满足
 *
 * @date 2022/4/25 上午9:34
 */
@Service
//@Transactional(rollbackOn = Exception.class)
public class PrimaryUserService {

    @Autowired
    private PrimaryUserRepository primaryUserRepository;

    public User save(User user){
        return primaryUserRepository.save(user);
    }

    public List<User> saveAll(List<User> users){
        return primaryUserRepository.saveAll(users);
    }

    public void delete(Long id){
        primaryUserRepository.deleteByUserId(id);
    }

    public List<User> findAll(){
        return primaryUserRepository.findAll();
    }
}
