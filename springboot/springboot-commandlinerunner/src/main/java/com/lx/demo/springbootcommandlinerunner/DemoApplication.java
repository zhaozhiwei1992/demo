package com.lx.demo.springbootcommandlinerunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动顺序:
 *
 * 19:05:38.104 [main] INFO com.lx.demo.springbootcommandlinerunner.DemoApplication - service to start.
 * 2019-08-20 19:05:38.564  INFO 27270 --- [           main] c.l.d.s.DemoApplication                  : Starting
 * DemoApplication on archlinux with PID 27270
 * (/home/lx7ly/workspace/demo/springboot/springboot-commandlinerunner/target/classes started by lx7ly in
 * /home/lx7ly/workspace/demo/springboot/springboot-commandlinerunner)
 * 2019-08-20 19:05:38.565  INFO 27270 --- [           main] c.l.d.s.DemoApplication                  : No active
 * profile set, falling back to default profiles: default
 * 2019-08-20 19:05:38.954  INFO 27270 --- [           main] c.l.d.s.service.UserService              : com.lx.demo
 * .springbootcommandlinerunner.service.UserService#<init>
 * 2019-08-20 19:05:39.077  INFO 27270 --- [           main] c.l.d.s.DemoApplication                  : Started
 * DemoApplication in 0.803 seconds (JVM running for 1.144)
 * 2019-08-20 19:05:39.078  INFO 27270 --- [           main] c.l.d.s.DemoApplication                  : com.lx.demo
 * .springbootcommandlinerunner.DemoApplication$CommandLineRunner1#run
 * 2019-08-20 19:05:39.079  INFO 27270 --- [           main] c.l.d.s.DemoApplication                  : com.lx.demo
 * .springbootcommandlinerunner.DemoApplication$CommandLineRunner2#run
 * 2019-08-20 19:05:39.079  INFO 27270 --- [           main] c.l.d.s.DemoApplication                  : service started
 * 可以看出 CommandLineRunner 中的方法会在 Spring Boot 容器加载之后执行，执行完成后项目启动完成, 并且可以通过order指定顺序
 *
 * {@link ApplicationRunner} 也可以做该处理
 */
@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		logger.info("service to start.");
		SpringApplication.run(DemoApplication.class, args);
		logger.info("service started");
	}

	@Component
	@Order(2)
	public class CommandLineRunner2 implements CommandLineRunner{

		@Override
		public void run(String... args) throws Exception {
			final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
			logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
		}
	}

	/**
	 * 启动方法根据日志验证是否符合
	 */
	@Component
	@Order(1)
	public class CommandLineRunner1 implements CommandLineRunner{

		@Override
		public void run(String... args) throws Exception {
			final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
			logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
		}
	}

	/**
	 * 也是在容器启动过程中执行, 可以搞好多事情
	 */
	@Component
	public class ApplicationRunner1 implements ApplicationRunner{

		@Override
		public void run(ApplicationArguments args) throws Exception {

			final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
			logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
		}
	}
}
