package com.lx.demo.springbootjmx;

import com.lx.demo.springbootjmx.spring.NotificationListenImpl;
import com.lx.demo.springbootjmx.spring.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;

import javax.management.NotificationListener;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootJmxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJmxApplication.class, args);
	}

	/**
	 *
	 * https://docs.spring.io/spring/docs/4.3.22.RELEASE/spring-framework-reference/htmlsingle/#jmx-notifications-listeners
	 *  <bean id="exporter" class="org.springframework.jmx.export.MBeanExporter">
	 *         <property name="beans">
	 *             <map>
	 *                 <entry key="bean:name=testBean1" value-ref="testBean"/>
	 *             </map>
	 *         </property>
	 *         <property name="notificationListenerMappings">
	 *             <map>
	 *                 <entry key="bean:name=testBean1">
	 *                     <bean class="com.example.ConsoleLoggingNotificationListener"/>
	 *                 </entry>
	 *             </map>
	 *         </property>
	 *     </bean>
	 *  照猫画虎， 通过编码方式初始化一个配置, 使用spring的方式 Registering listeners for notifications
	 * @return
	 */
	@Bean
	public MBeanExporter mBeanExporter(@Autowired NotificationListenImpl notificationListen, @Autowired Person person){
		MBeanExporter mBeanExporter = new MBeanExporter();
		Map<String, NotificationListener> map = new HashMap<>();
		map.put("bean:name=person", notificationListen);
		mBeanExporter.setNotificationListenerMappings(map);

		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bean:name=person", person);
		mBeanExporter.setBeans(hashMap);
		return mBeanExporter;
	}
}
