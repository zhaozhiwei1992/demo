package com.lx.demo.springbootsecurity.provicer;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Title: LoginAuthenticationProvider
 * @Package com/lx/demo/springbootsecurity/provicer/LoginAuthenticationProvider.java
 * @Description: 自定义密码验证
 * @author zhaozhiwei
 * @date 2021/9/20 下午2:53
 * @version V1.0
 */
public class CustomLoginAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


	private UserDetailsService userDetailsService;

	public CustomLoginAuthenticationProvider() {
	}

	/**
	 * @data: 2021/9/20-下午3:00
	 * @User: zhaozhiwei
	 * @method: additionalAuthenticationChecks
	  * @param userDetails :
	 * @param usernamePasswordAuthenticationToken :
	 * @return: void
	 * @Description: 自定义校验扩展, 密码
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
												  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

		if (usernamePasswordAuthenticationToken.getCredentials() == null) {
			this.logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		} else {
			String presentedPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
			// 用户名密码验证
			if (!matches(presentedPassword, userDetails.getPassword())) {
				this.logger.debug("Authentication failed: password does not match stored value");
				throw new BadCredentialsException(this.messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}
		}

	}

	private PasswordEncoder passwordEncoder;

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	/**
	 * 用户名密码验证
	 *
	 * @param presentedPassword 用户输入的密码
	 * @param encodedPassword   库中存放的密码
	 * @return true/false
	 */
	private boolean matches(String presentedPassword, String encodedPassword) {
		try {
			if (presentedPassword != null && presentedPassword.equals(encodedPassword)) {
				return true;
			} else if (this.passwordEncoder.matches(presentedPassword, encodedPassword)) {
				return true;
			}
		} catch (Exception e) {
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		return false;
	}


	@Override
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
	}

	/**
	 * @data: 2021/9/20-下午3:03
	 * @User: zhaozhiwei
	 * @method: retrieveUser
	  * @param username :
 * @param authentication :
	 * @return: org.springframework.security.core.userdetails.UserDetails
	 * @Description: 描述
	 */
	@Override
	protected UserDetails retrieveUser(String username,
									   UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		UserDetails loadedUser;
		try {
			// 调用loadUserByUsername时加入type前缀
			loadedUser = this.getUserDetailsService()
					.loadUserByUsername(/* authentication.getType() + "&:@" + */ username);
		} catch (Exception se) {
			final Object details = authentication.getDetails();
			if (this.getUserDetailsService() instanceof CachingUserDetailsService) {
				((CachingUserDetailsService) this.getUserDetailsService()).getUserCache().removeUserFromCache(username);
				return retrieveUser(username, authentication);
			}
			throw se;
		}

		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		} else {
			return loadedUser;
		}
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}
}
