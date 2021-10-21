package com.example.springboottest.listener;

import com.example.springboottest.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * 监听测试时一些信息
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see
 * @since 2017.09.13
 */
public class UserIntegrationTestListener extends AbstractTestExecutionListener {

    /**
     * The default implementation is <em>empty</em>. Can be overridden by
     * subclasses as necessary.
     */
    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
		/* no-op */
        ApplicationContext applicationContext = testContext.getApplicationContext();
        User person = applicationContext.getBean("user", User.class);
        System.err.println("user : " + person);
    }

    /**
     * The default implementation is <em>empty</em>. Can be overridden by
     * subclasses as necessary.
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        /* no-op */

        System.err.println("before : " + testContext.getTestMethod());

    }

    /**
     * The default implementation is <em>empty</em>. Can be overridden by
     * subclasses as necessary.
     */
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        /* no-op */
        System.err.println("after : " + testContext.getTestMethod());
    }

    public final int getOrder(){
        return HIGHEST_PRECEDENCE;
    }

}
