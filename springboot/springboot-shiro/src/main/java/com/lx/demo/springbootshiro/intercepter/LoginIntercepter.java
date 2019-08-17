package com.lx.demo.springbootshiro.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录校验
 */
@Slf4j
public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");

        log.info("准备登陆用户 => {}", loginName);
        UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            log.info("对用户[" + loginName + "]进行登录验证..验证开始");
            currentUser.login(token);
            log.info("对用户[" + loginName + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            log.error("对用户[" + loginName + "]进行登录验证..验证未通过,未知账户");
        } catch (IncorrectCredentialsException ice) {
            log.error("对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证");
        } catch (LockedAccountException lae) {
            log.error("对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            log.error("对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.error("对用户[" + loginName + "]进行登录验证..验证未通过,堆栈轨迹如下");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            log.info("用户[" + loginName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        } else {
            token.clear();
        }
        return true;
    }
}
