package com.lx.demo.springbootsecurity.configuration;

import com.lx.demo.springbootsecurity.provicer.CustomLoginAuthenticationProvider;
import com.lx.demo.springbootsecurity.security.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

/**
 * 缓存UserDetails
 **/
@Configuration
public class UserDetailsCacheConfig {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsCacheConfig.class);

	@Autowired
	private CustomUserDetailService customUserDetailService;

	/**
	 * @Description: 可以扩展为各种的cache, ehcache, redis等
	 */
	@Autowired
	private SimpleCacheManager cacheManager;

	@Bean
	public UserCache userCache() {
		try {
			Cache cache = cacheManager.getCache("bus.cloud.security.authUserCache");
			return new SpringCacheBasedUserCache(cache);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new NullUserCache();
	}

	/**
	 * @data: 2021/9/20-下午1:41
	 * @User: zhaozhiwei
	 * @method: userDetailsService

	 * @return: org.springframework.security.core.userdetails.UserDetailsService
	 * @Description: 通过反射将detailservice干到cache里
	 * 必须得有配套的provider才会使用cache
	 * org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 * 默认的DaoAuthenticationProvider不会走cache
	 *
	 * 必备三要素
	 * 1. 初始化CachingUserDetailsService, com.lx.demo.springbootsecurity.configuration.UserDetailsCacheConfig#userDetailsService()
	 * 2, 自定义provider
	 * {@see com.lx.demo.springbootsecurity.configuration.SecurityConfiguration#customLoginAuthenticationProvider()}
	 * 3. userdetailservice要显示的配置, com.lx.demo.springbootsecurity.configuration.SecurityConfiguration#userDetailsService
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		Constructor<CachingUserDetailsService> ctor = null;
		try {
			ctor = CachingUserDetailsService.class.getDeclaredConstructor(UserDetailsService.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Assert.notNull(ctor, "CachingUserDetailsService constructor is null");
		ctor.setAccessible(true);

		CachingUserDetailsService cachingUserDetailsService = BeanUtils.instantiateClass(ctor,
				customUserDetailService);
		cachingUserDetailsService.setUserCache(userCache());
		return cachingUserDetailsService;
	}

}