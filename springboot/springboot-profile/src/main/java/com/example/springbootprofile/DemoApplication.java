package com.example.springbootprofile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @see org.springframework.context.EnvironmentAware
 */
@SpringBootApplication
public class DemoApplication implements EnvironmentAware {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void setEnvironment(Environment environment) {
		Arrays.stream(environment.getActiveProfiles()).forEach(System.out::println);
//		logger.info(environment.getActiveProfiles().toString());

		if(environment instanceof ConfigurableEnvironment){
			ConfigurableEnvironment configurableEnvironment = ConfigurableEnvironment.class.cast(environment);
		}
	}
}

