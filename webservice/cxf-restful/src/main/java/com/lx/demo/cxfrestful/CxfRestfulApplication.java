package com.lx.demo.cxfrestful;

import com.lx.demo.cxfrestful.resource.SayHelloImpl;
import com.lx.demo.cxfrestful.resource.SayHelloImpl2;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * springmvc本身就支持了rest, 用cxf就是闲的
 * 参考 http://cxf.apache.org/docs/springboot.html
 */
@SpringBootApplication
public class CxfRestfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxfRestfulApplication.class, args);
	}


	@Autowired
	private Bus bus;

	/**
	 * <servlet>
	 *         <servlet-name>CXFServlet</servlet-name>
	 *         <servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>
	 *     </servlet>
	 *
	 *     <servlet-mapping>
	 *         <servlet-name>CXFServlet</servlet-name>
	 *         <url-pattern>/ws/*</url-pattern>
	 *     </servlet-mapping>
	 */
	@Bean
	public Server rsServer() {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setAddress("/ws");
		// Register 2 JAX-RS root resources supporting "/sayHello/{id}" and "/sayHello2/{id}" relative paths
		endpoint.setServiceBeans(Arrays.asList(new SayHelloImpl(), new SayHelloImpl2()));
//		endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
		return endpoint.create();
	}
}
