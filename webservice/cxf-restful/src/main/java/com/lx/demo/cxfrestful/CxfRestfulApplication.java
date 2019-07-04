package com.lx.demo.cxfrestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springmvc本身就支持了rest, 用cxf就是闲的
 */
@SpringBootApplication
public class CxfRestfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxfRestfulApplication.class, args);
	}

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
//	public CXFServlet cxfServlet(){
//		final CXFServlet cxfServlet = new CXFServlet();
//		cxfServlet
//	}
}
