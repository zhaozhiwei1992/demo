package com.lx.demo.springbootjdbc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootjdbc.controller
 * @Description:
 * mysql数据库
 * sql注入测试
 * @date 2022/6/22 下午1:41
 */
@RestController
@RequestMapping("/sql/inject")
public class SqlInjectionController {

    private static final Logger log = LoggerFactory.getLogger(SqlInjectionController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @data: 2022/6/22-下午1:41
     * @User: zhaozhiwei
     * @method: sqlConcatTest

     * @return: void
     * @Description:
     * 拼接型注入测试, 一般使用预编译或者两边加单引号可以规避很多问题
     *
     * 1. 测试能否通过该接口，删除表
     * 入参: 0;drop table t_user;
     * 编码：　zhangsan%3Bdrop%20table%20t_use%3B
     *
     * 2. 拖库
     * 入参: 0 or name is not null
     * 编码: 0%20or%20name%20is%20not%20null
     *
     * 入参: 0
     */
    @GetMapping("/find-by-name")
    public void findByName(String name){
        String sql = "select * from t_user where name = " + name;
//       这种拼接的方式, 如果传入是字符串如张三, 没法执行的，会被当作表字段, 如果参数是0  where name = 0会查所有
//        jdbcTemplate.execute(sql);

//        采用单引号括住的方式，可以规避一部分注入问题
//        String sql = "select * from t_user where name = '" + name + "'";
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        log.info("查询结果 {}", maps);
    }
}
