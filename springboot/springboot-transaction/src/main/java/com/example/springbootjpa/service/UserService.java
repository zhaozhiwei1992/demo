package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @Title: SuccessUserService
 * @Package com/example/springbootjpa/service/SuccessUserService.java
 * @Description: 第三个请求, 用来做测试
 * @author zhaozhiwei
 * @date 2023/5/6 上午9:43
 * @version V1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        final User save = userRepository.save(user);
        return save;
    }

    /**
     * @data: 2023/5/6-下午2:19
     * @User: zhaozhiwei
     * @method: asyncSave
      * @param user :
     * @return: com.example.springbootjpa.domain.User
     * @Description: 异步保存
     */
    @Async
    @Transactional
    public User asyncSave(User user){
        final User save = userRepository.save(user);
        return save;
    }

    /**
     * @data: 2023/5/6-上午11:05
     * @User: zhaozhiwei
     * @method: save2
      * @param user :
     * @return: com.example.springbootjpa.domain.User
     * @Description: 开启新事物，事务内部嵌套使用测试
     * 增加 Propagation.REQUIRES_NEW 后, 如果外部事务回滚不影响这个事务的提交, 完全独立
     * transaction save -> save2
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User save2(User user){
        final User save = userRepository.save(user);
        return save;
    }

    public Collection<User> findAll(){
        return userRepository.findAll();
    }
}
