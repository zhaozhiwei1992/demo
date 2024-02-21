package com.example.service;

import com.example.config.OracleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class OracleConnTestService {

    @Autowired
    private OracleProperties oracleProperties;

    /**
     * @param nThreads  :  并行线程数
     * @data: 2024/2/18-下午9:06
     * @User: zhaozhiwei
     * @method: exec
     * @return: void
     * @Description: 测试数据库的连接释放
     */
    public void exec(int nThreads) {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        // 模拟创建和释放数据库连接
        for (int j = 0; j < nThreads; j++) {
            executorService.execute(() -> {

                // 模拟创建数据库连接的耗时操作
                try {
                    long startTime = System.currentTimeMillis();
                    final Connection connection = DriverManager.getConnection(oracleProperties.getUrl()
                            , oracleProperties.getUsername(), oracleProperties.getPassword());
                    long connectionCreationTime = System.currentTimeMillis() - startTime;
                    System.out.println("Connection created in " + connectionCreationTime + " ms");

//                    Thread.sleep(1000);

                    // 模拟释放数据库连接的耗时操作
                    startTime = System.currentTimeMillis();
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("数据库连接释放异常");
                            e.printStackTrace();
                        }
                    }
                    long releaseTime = System.currentTimeMillis() - startTime;
                    System.out.println("Connection released in " + releaseTime + " ms");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // 关闭线程池
        executorService.shutdown();
    }
}
