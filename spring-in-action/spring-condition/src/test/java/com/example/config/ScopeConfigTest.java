package com.example.config;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/2/17 上午11:48
 */
public class ScopeConfigTest{

    @Test
    public void singletonBeanTest(){
        final AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(ScopeConfig.class);
        final Object singletonBean1 = annotationConfigApplicationContext.getBean("singletonBean");
        final Object singletonBean2 = annotationConfigApplicationContext.getBean("singletonBean");
        Assert.assertEquals(singletonBean1, singletonBean2);
        annotationConfigApplicationContext.close();
    }

    /**
     * @data: 2022/2/17-下午2:23
     * @User: zhaozhiwei
     * @method: prototypeBeanTest

     * @return: void
     * @Description: 每次请求都是一个新的对象
     */
    @Test
    public void prototypeBeanTest(){
        final AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(ScopeConfig.class);
//        GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once
//        annotationConfigApplicationContext.refresh();
        final Object prototypeBean1 = annotationConfigApplicationContext.getBean("prototypeBean");
        final Object prototypeBean2 = annotationConfigApplicationContext.getBean("prototypeBean");
        Assert.assertNotEquals(prototypeBean1, prototypeBean2);
        annotationConfigApplicationContext.close();
    }

    protected MockHttpSession session;
    protected MockHttpServletRequest request;

    protected void startSession() {
        session = new MockHttpSession();
    }

    protected void endSession() {
        session.clearAttributes();
        session = null;
    }

    protected void startRequest() {
        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    protected void endRequest() {
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).requestCompleted();
        RequestContextHolder.resetRequestAttributes();
        request = null;
    }

    /**
     * @data: 2022/2/17-下午5:04
     * @User: zhaozhiwei
     * @method: sessionBeanTest

     * @return: void
     * @Description: 描述
     * Using a custom scope
     *
     * After you write and test one or more custom Scope implementations, you need to make the Spring container aware
     * of your new scope(s). The following method is the central method to register a new Scope with the Spring
     * container:
     * https://stackoverflow.com/questions/5136944/spring-test-session-scope-bean-using-junit
     * Now you can use these methods in your test code:
     *
     * startSession();
     * startRequest();
     * // inside request
     * endRequest();
     * startRequest();
     * // inside another request of the same session
     * endRequest();
     * endSession();
     *
     *  //todo 验证不通过, junit, 后续处理, 或者在web项目中测试
     */
    @Test
    public void sessionBeanTest(){

        final AnnotationConfigWebApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigWebApplicationContext();

        annotationConfigApplicationContext.register(ScopeConfig.class);
        annotationConfigApplicationContext.refresh();

        startSession();

        final Object sessionBean1 = annotationConfigApplicationContext.getBean("sessionBean");
        final Object sessionBean2 = annotationConfigApplicationContext.getBean("sessionBean");

        final Object sessionBean = session.getAttribute("sessionBean");
        System.out.println(sessionBean);
        // 同一个session bean应该是同一个
        Assert.assertEquals(sessionBean1, sessionBean2);

        endSession();

        annotationConfigApplicationContext.close();
    }

    public void requestBeanTest(){

    }

}