package com.example.springbootbucket4j.config;

import com.example.springbootbucket4j.aop.Bucket4jFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class WebMvcConfig {

	public WebMvcConfig() {
		System.out.println("init WebMvcConfig ...");
	}

	@Bean
	public CacheManager simpleCacheManager(){
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		ConcurrentMapCache cacheBucket = new ConcurrentMapCache("cache-bucket");
		simpleCacheManager.setCaches(Collections.singletonList(cacheBucket));
		return simpleCacheManager;
	}

	/**
	 * @data: 2022/8/18-上午10:18
	 * @User: zhaozhiwei
	 * @method: simpleCacheEvict

	 * @return: void
	 * @Description: concurrentMapCache只能自己来实现过期
	 */
//	@CacheEvict(allEntries = true, value = {"cache-bucket"})
//	@Scheduled(fixedDelay = 6 * 1000 ,  initialDelay = 500)
	public void simpleCacheEvict() {
		System.out.println("Flush Cache " + new SimpleDateFormat().format(new Date()));
	}

	/**
	 *As described earlier, any Servlet or Filter beans are registered with the servlet container
	 * automatically. To disable registration of a particular Filter or Servlet bean, create a registration
	 * bean for it and mark it as disabled, as shown in the following example:
	 * @return
	 */
	@Bean
	public FilterRegistrationBean registration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		final Bucket4jFilter bucket4jFilter = new Bucket4jFilter(simpleCacheManager());
		registration.setFilter(bucket4jFilter);
		registration.addUrlPatterns("/echo/*");
		registration.setName("bucketCache");
		registration.setOrder(1);
		return registration;
	}

}
