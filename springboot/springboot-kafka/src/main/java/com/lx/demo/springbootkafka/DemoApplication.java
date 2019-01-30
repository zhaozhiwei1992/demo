package com.lx.demo.springbootkafka;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * 启动服务就执行命令
	 * @param kafkaTemplate
	 * @return
	 */
	@Bean
	public ApplicationRunner sendMessage(KafkaTemplate kafkaTemplate){
	    return (args -> kafkaTemplate.send("saveUser", "a test message"));
	}
}

