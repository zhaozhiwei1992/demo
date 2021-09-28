package com.example.springbootaop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Title: DemoApplication
 * @Package com/example/springbootaop/DemoApplication.java
 * @Description:
//注解开启cglib代理,开启 exposeProxy = true,暴露代理对象 (否则AopContext.currentProxy()) 会抛出异常
//java.lang.IllegalStateException: Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to make it available.
 * @author zhaozhiwei
 * @date 2021/9/27 上午9:43
 * @version V1.0
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

