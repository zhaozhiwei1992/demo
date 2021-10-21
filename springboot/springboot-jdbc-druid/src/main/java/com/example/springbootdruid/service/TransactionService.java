package com.example.springbootdruid.service;

import com.example.springbootdruid.domain.User;
import com.example.springbootdruid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 事务管理器
     * transactionManager
     */
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    /**
     * @data: 2021/10/12-下午4:11
     * @User: zhaozhiwei
     * @method: saveBySpringJDBC
      * @param record :
     * @return: int
     * @Description: 手动开启事务,并操作
     */
    private int saveByTransactionAPI(User record) {

        // api事务控制
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

        jdbcTemplate.execute("insert into t_user(name) values (?)", (PreparedStatement preparedStatement)->{
            preparedStatement.setString(1, record.getName());
            int i = preparedStatement.executeUpdate();
            return i;
        });

        // 有报错时候事务不能提交
//        if(0<1){
//            throw new NullPointerException();
//        }
        platformTransactionManager.commit(transactionStatus);
        return 0;
    }


}
