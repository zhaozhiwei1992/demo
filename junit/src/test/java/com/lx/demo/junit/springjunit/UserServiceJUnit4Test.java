package com.lx.demo.junit.springjunit;

import com.lx.demo.junit.domain.User;
import com.lx.demo.junit.service.impl.MockUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/testing.html#testing
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MockUserService.class)
public class UserServiceJUnit4Test {

    @Autowired
    private MockUserService mockUserService;

    @Test
    public void testSave(){
        User user = new User();
        user.setId(1L);
        user.setName("zhangsan");

        Assert.assertTrue(mockUserService.save(user));
        Assert.assertFalse(mockUserService.save(user));
    }
}
