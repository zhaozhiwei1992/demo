package com.lx.demo.springbootshiro.config;

import com.lx.demo.springbootshiro.domain.Permission;
import com.lx.demo.springbootshiro.domain.Role;
import com.lx.demo.springbootshiro.domain.User;
import com.lx.demo.springbootshiro.filter.KickoutSessionControlFilter;
import com.lx.demo.springbootshiro.service.UserService;
import org.apache.shiro.mgt.SecurityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class CustomShiroConfiguration {

    /**
     * org.apache.shiro.UnavailableSecurityManagerException: No SecurityManager accessible to the calling code,
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    //缓存相关开始
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/logout");
        return kickoutSessionControlFilter;
    }

    //缓存相关结束

    /**
     * Filter Name	Class
     * anon	org.apache.shiro.web.filter.authc.AnonymousFilter
     * authc	org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * authcBasic	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * perms	org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
     * port	org.apache.shiro.web.filter.authz.PortFilter
     * rest	org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles	org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
     * ssl	org.apache.shiro.web.filter.authz.SslFilter
     * user	org.apache.shiro.web.filter.authc.UserFilter
     * anon:所有 url 都都可以匿名访问
     * authc: 需要认证才能进行访问
     * user:配置记住我或认证通过可以访问
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("注入Shiro的Web过滤器-->shiroFilter", ShiroFilterFactoryBean.class);
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的连接,逻辑也可以自定义，例如返回上次请求的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //用户访问未对其授权的资源时,所显示的连接
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /*定义shiro过滤器,例如实现自定义的FormAuthenticationFilter，需要继承FormAuthenticationFilter **本例中暂不自定义实现，在下一节实现验证码的例子中体现 */

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //限制同一帐号同时在线的个数。
        filtersMap.put("kickout", kickoutSessionControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        /*定义shiro过滤链 Map结构 * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的 *
        anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种 * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org
        .apache.shiro.web.filter.authc.FormAuthenticationFilter */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/login", "anon");//anon 可以理解为不拦截

        //swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");

        //权限认证
        //用户为ROLE_USER 角色可以访问。由用户角色控制用户行为。
//        filterChainDefinitionMap.put("/user/**", "authc,roles[ROLE_USER]");
//        filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");

        filterChainDefinitionMap.put("/**", "authc,kickout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

//    @Bean
//    public EhCacheManager ehCacheManager() {
//        EhCacheManager cacheManager = new EhCacheManager();
//        return cacheManager;
//    }

    @Bean
    public CustomShiroRealm customShiroRealm() {
        return new CustomShiroRealm();
    }

    @Bean
    public SecurityManager securityManager(CustomShiroRealm userRealm) {
        log.info("注入Shiro的Web过滤器-->securityManager", ShiroFilterFactoryBean.class);
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        //注入缓存管理器;
//        securityManager.setCacheManager(ehCacheManager());//这个如果执行多次，也是同样的一个对象;
        // 自定义缓存实现 使用redis, 这里使用缓存以后会缓存权限, 如果数据库有变更记得刷缓存
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * Shiro生命周期处理器 * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解
     * (如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 异常处理
     *
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
        mappings.setProperty("UnauthorizedException", "403");
        r.setExceptionMappings(mappings);  // None by default
        r.setDefaultErrorView("error");    // No default
        r.setExceptionAttribute("ex");     // Default is "exception"
        //r.setWarnLogCategory("example.MvcLogger");     // No default
        return r;
    }

    /**
     * 自定义方式
     */
    class CustomShiroRealm extends AuthorizingRealm {

        @Autowired
        private UserService userService;

        /**
         * 授权, 通过角色授权, 这里设置角色信息即可
         * 参考: https://gitee.com/ityouknow/spring-boot-examples/blob/master/spring-boot-shiro/src/main/java/com/neo
         * /config/MyShiroRealm.java
         * https://blog.csdn.net/fu_fei_wen/article/details/77571989
         *
         * @param principals
         * @return
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            // 简化版本, demo可以不读取数据库直接写死撸：
//            String role = "admin";
//            String permission = "add";
//            log.info("{}, role:{}, permission:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), role,
//                    permission);
//
//            //设置权限信息
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            simpleAuthorizationInfo.addRole(role);
//            simpleAuthorizationInfo.addStringPermission(permission);
//            return simpleAuthorizationInfo;

            // 常规版本
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            String loginName = String.valueOf(principals.getPrimaryPrincipal());
            final User userInfo = userService.findByLoginName(loginName);
            for(Role role:userInfo.getRoleList()){
                authorizationInfo.addRole(role.getName());
                for(Permission p:role.getPermissions()){
                    authorizationInfo.addStringPermission(p.getPermission());
                }
            }
            return authorizationInfo;

        }

        /**
         * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
         *
         * @param token
         * @return
         * @throws AuthenticationException
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
                throws AuthenticationException {
            //简化版本：
//            String name = "zhangsan";
//            String pwd = "11";
//
//            UsernamePasswordToken upt = (UsernamePasswordToken) token;
//            String username = upt.getUsername();
//            String password = new String(upt.getPassword());
//
//            if (name.equals(username) && pwd.equals(password)) {
//                return new SimpleAuthenticationInfo(username, password, "zhangsansan");
//            }
//            return null;

            //获取用户的输入的账号.
            String username = (String)token.getPrincipal();
            log.info("{}", token.getCredentials());
            //通过username从数据库中查找 User对象，如果找到，没找到.
            //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
            User userInfo = userService.findByLoginName(username);
            log.info("----->>userInfo= {}", userInfo);
            if(userInfo == null){
                return null;
            }
            return new SimpleAuthenticationInfo(
                    userInfo.getLoginName(), //用户名
                    userInfo.getPassword(), //密码
                    ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                    getName()  //realm name
            );
        }
    }
}
