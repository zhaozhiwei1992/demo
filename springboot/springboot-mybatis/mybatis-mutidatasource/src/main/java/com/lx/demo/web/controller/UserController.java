package com.lx.demo.web.controller;

import com.lx.demo.annotation.DS;
import com.lx.demo.domain.User;
import com.lx.demo.mapper.dynamic.UserMapperDynamic;
import com.lx.demo.mapper.master.UserMapperMaster;
import com.lx.demo.mapper.slaver.UserMapperSlaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * curl http://127.0.0.1:8080/user/1
 *
 * 返回结果： {"id":1,"name":"admin","password":"21232f297a57a5a743894a0e4a801fc3","realname":"helloworld",
 * "avatar":null,"mobile":"13073804251","sex":"男","status":1,"createTime":"2017-09-04T21:32:15.000+0000"}%
 */
@RestController
public class UserController {

    /**
     * 同时注入主从库, 代码控制多数据源使用
     * 该方式只是多来源，其实没有动态切换
     */
    @Autowired
    private UserMapperMaster userMapperMaster;

    @Autowired
    private UserMapperSlaver userMapperSlaver;

    /**
     * 获取数据走从域
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public User selectOne(@PathVariable Long id){
        final User user = userMapperSlaver.getOne(id);
        return user;
    }

    @GetMapping("/users")
    public List<User> users(){
        return userMapperSlaver.getAll();
    }

    /**
     * 写入数据走主域
     */
    @PostMapping("/users")
    public void save(@RequestBody User user){
        userMapperMaster.insert(user);
    }

    @Autowired
    private UserMapperDynamic userMapperDynamic;

    /**
     * 增加动态主从数据源注解，　配置后自动切换数据源
     * {@see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineTargetDataSource()}
     * {@see com.lx.demo.datasourcerouting.MasterSlaverDataSource#determineCurrentLookupKey()}
     * @param user
     */
    @DS("slaver")
    @PostMapping("/users-dy")
    public void saveByDynamic(@RequestBody User user){
        userMapperDynamic.insert(user);
    }

    @DS("slaver")
    @GetMapping("/users-dy")
    public List<User> usersByDynamic(){
        return userMapperDynamic.getAll();
    }


}
