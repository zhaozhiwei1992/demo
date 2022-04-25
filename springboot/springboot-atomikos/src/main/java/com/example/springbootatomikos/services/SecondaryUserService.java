package com.example.springbootatomikos.services;

import com.example.springbootatomikos.domain.User;
import com.example.springbootatomikos.repository.secondary.SecondaryUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

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
public class SecondaryUserService {

    private static final Logger logger = LoggerFactory.getLogger(SecondaryUserService.class);

    @Autowired
    private SecondaryUserRepository secondaryUserRepository;

    private Random random = new Random();

    public User save(User user){
        final int i = random.nextInt(10);
        if(i < 3){
            logger.error("抛出异常, 当前对象 {} 都不应该保存", user);
            throw new RuntimeException("对象不应该被保存");
        }
        return secondaryUserRepository.save(user);
    }

    public List<User> saveAll(List<User> users){
        return secondaryUserRepository.saveAll(users);
    }

    public void delete(Long id){
        secondaryUserRepository.deleteByUserId(id);
    }

    public List<User> findAll(){
        return secondaryUserRepository.findAll();
    }
}
