package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/4 下午5:20
 */
@Service
public class UserService {

    public List<User> findUsers(int startIndex, int count){
        return null;
    }

    /**
     * @data: 2022/3/5-下午8:25
     * @User: zhaozhiwei
     * @method: findOne
      * @param id :
     * @return: com.example.domain.User
     * @Description: 描述
     * 403 - Access is denied
     */
//    @Secured("ROLE_ADMIN")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasPermission(targetObject, 'findOne')")
    public User findOne(int id){
        System.out.println("请求到方法 findOne");
        return null;
    }

    public User save(User user){
        return null;
    }
}
