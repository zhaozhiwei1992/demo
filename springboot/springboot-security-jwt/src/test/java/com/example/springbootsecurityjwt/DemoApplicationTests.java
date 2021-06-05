package com.example.springbootsecurityjwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import java.util.Iterator;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void antPathMatcher() {
		AntPathMatcher antPathMatcher = new AntPathMatcher();

		// path路径是否符合pattern的规范
		boolean match = antPathMatcher.match("/user/*", "/user/a");
		System.out.println(match);
		match = antPathMatcher.match("/user/**", "/user/a/b");
		System.out.println(match);
		match = antPathMatcher.match("/user/{id}", "/user/1");
		System.out.println(match);
		match = antPathMatcher.match("/user/name", "/user/a");
		System.out.println(match);

		boolean pattern = antPathMatcher.isPattern("user/{id}");
		System.out.println(pattern);

		// 匹配是不是以path打头的地址
		boolean matchStart = antPathMatcher.matchStart("/1user/a", "/user");
		System.out.println(matchStart);

		// 对路径进行合并 --> /user/a/b
		String combine = antPathMatcher.combine("/user", "a/b");
		System.out.println(combine);

		// 找出模糊匹配中 通过*或者? 匹配上的那一段配置
		String extractPathWithinPattern = antPathMatcher.extractPathWithinPattern("/user/?", "/user/1");
		System.out.println(extractPathWithinPattern);

		// 找出模糊匹配中 找到匹配上的项 如果匹配规则和要匹配的项规则不一致 会报错
		Map<String, String> extractUriTemplateVariables = antPathMatcher
				.extractUriTemplateVariables("{appName:[\\p{L}\\.]+}-sources-{version:[\\p{N}\\.]+}.jar",
						"demo-sources-1.0.0.jar");
		if (null != extractUriTemplateVariables) {
			Iterator<String> iterator = extractUriTemplateVariables.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				System.out.println("extractUriTemplateVariables:" + extractUriTemplateVariables.get(key));
			}
		}
	}

}
