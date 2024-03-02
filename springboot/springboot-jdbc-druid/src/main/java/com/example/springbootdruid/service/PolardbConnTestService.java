package com.example.springbootdruid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: DatabaseConnTestService
 * @Package com/example/service/DatabaseConnTestService.java
 * @Description: polardb数据库连接测试
 * @date 2024/2/18 下午9:04
 */
@Component
public class PolardbConnTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @data: 2024/2/18-下午9:06
     * @User: zhaozhiwei
     * @method: exec
     * @return: void
     * @Description: 测试数据库的连接释放
     * 不管带不带事务,都会com.alibaba.druid.pool.DruidDataSource#recycle(com.alibaba.druid.pool.DruidPooledConnection)
     */
    @Transactional
    public void exec() {
        // 测试连接超时
        final long i = (long) (Math.random() * 500 + 100);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        jdbcTemplate.execute("select 1");
    }
}
