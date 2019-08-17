package com.lx.demo.springbootshiro.config;

import org.apache.shiro.mgt.SecurityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class CustomShiroConfiguration {

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

        /*定义shiro过滤链 Map结构 * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的 *
        anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种 * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org
        .apache.shiro.web.filter.authc.FormAuthenticationFilter */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/login", "anon");//anon 可以理解为不拦截
        filterChainDefinitionMap.put("/reg", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/pages/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/dists/img/*", "anon");
        filterChainDefinitionMap.put("/**", "authc");

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
            //在实际开发中，开发者根据自身情况自行获取用户信息。此处简化如下：
            String role = "admin";
            String permission = "add";

            log.info("{}, role:{}, permission:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), role,
                    permission);

            //设置权限信息
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole(role);
            simpleAuthorizationInfo.addStringPermission(permission);

            return simpleAuthorizationInfo;
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
            //在实际开发中，开发者根据自身情况自行获取用户信息。此处简化如下：
            String name = "zhangsan";
            String pwd = "11";

            UsernamePasswordToken upt = (UsernamePasswordToken) token;
            String username = upt.getUsername();
            String password = new String(upt.getPassword());

            if (name.equals(username) && pwd.equals(password)) {
                return new SimpleAuthenticationInfo(username, password, "zhangsansan");
            }
            return null;
            //获取用户的输入的账号.
//            String username = (String)token.getPrincipal();
//            System.out.println(token.getCredentials());
//            //通过username从数据库中查找 User对象，如果找到，没找到.
//            //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//            UserInfo userInfo = userInfoService.findByUsername(username);
//            System.out.println("----->>userInfo="+userInfo);
//            if(userInfo == null){
//                return null;
//            }
//            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                    userInfo, //用户名
//                    userInfo.getPassword(), //密码
//                    ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
//                    getName()  //realm name
//            );
//            return authenticationInfo;
        }
    }
}
