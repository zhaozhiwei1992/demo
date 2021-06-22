package com.example.springbootjcrontab;

import com.example.springbootjcrontab.jcrontab.TaskManagerInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	/**
	 * @data: 2021/6/22-下午8:49
	 * @User: zhaozhiwei
	 * @method: taskManagerInit

	 * @return: com.example.springbootjcrontab.jcrontab.TaskManagerInit
	 * @Description: 初始化bean
	 * 	<bean id="task.jcrontab.TaskManagerInit" class="gov.mof.fasp2.task.jcrontab.TaskManagerInit"
	 * 	init-method="init" destroy-method="destroy"></bean>
	 */
	@Bean(initMethod = "init")
	public TaskManagerInit taskManagerInit(){
		return new TaskManagerInit();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
