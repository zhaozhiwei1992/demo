package com.example.springbootbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
@Configuration
public class SpringbootBeanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBeanApplication.class, args);
	}

//	@Bean
//	public DataSource dataSource(){
//
//		DataSource dataSource = DataSourceBuilder.create()
//				.driverClassName("com.mysql.jdbc.Driver")
//				.url("jdbc:mysql://localhost:3306/user")
//				.username("root")
//				.password("root").build();
//		return dataSource;
//	}

	/**
	 * 主数据源
	 *
	 * 多个数据源配置时必须有一个默认的，打标记为primary
	 * @return
	 */
	@Bean
	@Primary
	public DataSource masterDataSource(){
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.jdbc.Driver")
				.url("jdbc:mysql://localhost:3306/test1")
				.username("root")
				.password("root").build();
	}

}
