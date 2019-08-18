package com.lx.demo.springbootshiro.web.rest;

import com.lx.demo.springbootshiro.domain.User;
import com.lx.demo.springbootshiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/users")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    @DeleteMapping("/users/{id}")
    public User delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }

    /**
     * 修改用户
     * 这里如果是admin角色，那么就会通过校验修改用户
     * @param user
     * @return
     */
    @PutMapping("/users")
    @RequiresRoles("admin")
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/users")
    public List<User> findAll(){
        return new ArrayList<>(userService.findAll());
    }
}
