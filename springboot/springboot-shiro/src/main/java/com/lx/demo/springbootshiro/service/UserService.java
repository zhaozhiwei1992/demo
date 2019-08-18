package com.lx.demo.springbootshiro.service;

import com.lx.demo.springbootshiro.domain.Permission;
import com.lx.demo.springbootshiro.domain.Role;
import com.lx.demo.springbootshiro.domain.User;
import org.apache.commons.collections.ArrayStack;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    /**
     * 用户存储仓库
     */
    private static Map<Long, User> userRepository = new ConcurrentHashMap<Long, User>();

    /**
     * 初始化管理员
     */
    public UserService() {
        final User user = new User();
        user.setId(0L);
        user.setAge(0);
        user.setName("ttang");
        user.setLoginName("ttang");
        user.setPassword("99");
        user.setSalt("mysalt");
        // 用户具有admin和user角色, admin具有所有权限
        user.setRoleList(Arrays.asList(
//                new Role("user", Arrays.asList(new Permission("add"), new Permission("delete"))),
                new Role("admin", Arrays.asList(new Permission("add")))
                )
        );

        userRepository.putIfAbsent(user.getId(), user);
    }

    public User save(User user) {
        return userRepository.putIfAbsent(user.getId(), user);
    }

    public User delete(Long id) {
        return userRepository.remove(id);
    }

    public User update(User user) {
        return userRepository.replace(user.getId(), user);
    }

    public Collection<User> findAll() {
        return userRepository.values();
    }

    public User findOne(Long id) {
        return userRepository.get(id);
    }

    /**
     * 根据登录名找对象
     * @param loginName
     * @return
     */
    public User findByLoginName(String loginName) {
        return userRepository.values()
                .stream()
                .filter(user -> loginName.equals(user.getLoginName()))
                .findFirst()
                .get();
    }
}
