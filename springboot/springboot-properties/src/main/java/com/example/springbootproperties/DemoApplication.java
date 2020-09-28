package com.example.springbootproperties;

import com.example.springbootproperties.listener.EchoControllerModifyListener;
import com.example.springbootproperties.listener.LocalEnvironmentPrepareEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 三种方式进行属性扩展变更实现
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		final SpringApplication springApplication = new SpringApplication(DemoApplication.class);
		springApplication.addListeners(new EchoControllerModifyListener());
//		springApplication.addListeners(new LocalEnvironmentPrepareEventListener());
		springApplication.run(args);
	}

}
