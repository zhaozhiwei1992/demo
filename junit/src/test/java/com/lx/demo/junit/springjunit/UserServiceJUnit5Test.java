package com.lx.demo.junit.springjunit;

import com.lx.demo.junit.domain.User;
import com.lx.demo.junit.service.impl.MockUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/testing.html#testing
 *
 * [@ExtendWith(SpringExtension.class) @ContextConfiguration(classes = MockUserService.class)] 必须同时使用在junit5中才会生效
 * [@SpringJUnitConfig(MockUserService.class)]  回去加载类
 *
 */
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = MockUserService.class)
@SpringJUnitConfig(MockUserService.class)
public class UserServiceJUnit5Test {

    @Autowired
    private MockUserService mockUserService;

    @Test
    public void testSave(){
        User user = new User();
        user.setId(1L);
        user.setName("zhangsan");

        assertTrue(mockUserService.save(user));
        assertFalse(mockUserService.save(user));
    }
}
