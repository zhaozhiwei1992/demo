package com.example.springcloudclient;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SpringCloudClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudClientApplication.class, args);
	}

	@Autowired
	private ContextRefresher contextRefresher;

	@Scheduled(cron = "*/1 * * * * *")
	public void autoRefreshProfile(){
		Set<String> updateparams = contextRefresher.refresh();
		System.out.println("配置已刷新---");
	}
}
