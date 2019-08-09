package com.lx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 * 屏蔽掉{@linkk DataSourceAutoConfiguration.class} 防止application.yaml中配置spring.datasource后初始化单数据源
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MutiDatasourceMain {
    public static void main(String[] args) {
        SpringApplication.run(MutiDatasourceMain.class);
    }
}
