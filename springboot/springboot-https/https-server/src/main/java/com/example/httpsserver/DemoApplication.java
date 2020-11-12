package com.example.httpsserver;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	/**
	 * https://github.com/spring-projects/spring-boot/blob/v2.0.8.RELEASE/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors
	 * /src/main/java/sample/tomcat/multiconnector/SampleTomcatTwoConnectorsApplication.java
	 * @return
	 */
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
//		return tomcat;
//	}

	@Bean
	public Connector connector(){
		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

	/**
	 * https://github.com/spring-projects/spring-boot/blob/v2.0.8.RELEASE/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors
	 * /src/main/java/sample/tomcat/multiconnector/SampleTomcatTwoConnectorsApplication.java
     *
	 * https://www.jianshu.com/p/8d4aba3b972d
	 * @return
	 */
	@Bean
	public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
		TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint=new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection=new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(connector);
		return tomcat;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
