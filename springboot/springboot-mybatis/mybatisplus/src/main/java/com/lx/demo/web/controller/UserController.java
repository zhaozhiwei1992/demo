package com.lx.demo.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.demo.domain.User;
import com.lx.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * curl http://127.0.0.1:8080/user/1
 *
 * 返回结果： {"id":1,"name":"admin","password":"21232f297a57a5a743894a0e4a801fc3","realname":"helloworld",
 * "avatar":null,"mobile":"13073804251","sex":"男","status":1,"createTime":"2017-09-04T21:32:15.000+0000"}%
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public User selectOne(@PathVariable int id){
        final User user = userMapper.selectById(id);
        return user;
    }

    @GetMapping("/users")
    public IPage users(){
        // 分页设置可以加入到全局threadlocal中
        Page<User> page = new Page<>(1, 2);
        final IPage<User> selectPage = userMapper.selectPage(page, null);
        return selectPage;
    }

    /**
     * @data: 2023/3/9-下午5:03
     * @User: zhaozhiwei
     * @method: usersMultiParam

     * @return: List<User>
     * @Description: 多条件查询示例
     * 动态方式跟jpa的方法查询方式各有千秋
     *
     * jpa的方法签名方式, 如果配置有误，服务都起不来，用起来个人感觉更爽, 习惯问题
     */
    public List<User> usersMultiParam(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        final java.util.List<User> tList = userMapper.selectList(queryWrapper);
    }
}
