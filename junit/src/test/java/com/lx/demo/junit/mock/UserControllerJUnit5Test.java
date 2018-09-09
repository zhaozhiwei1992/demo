package com.lx.demo.junit.mock;

import com.lx.demo.junit.controller.UserController;
import com.lx.demo.junit.domain.User;
import com.lx.demo.junit.service.UserRemoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * 通过junit5的机制实现测试无网络访问环境下远程接口
 * [@SpringJUnitConfig(UserController.class)] 把controller加入到beancontext环境中
 */
@SpringJUnitConfig({UserController.class, UserControllerJUnit5Test.MockUserRemoteService.class})
public class UserControllerJUnit5Test {

    @Autowired
    private UserController userController;

    @Test
    public void testFindAll(){
        List<User> all = userController.findAll();
        assertTrue(all.size() == 1);
        assertTrue(all.get(0).getId() == 1L);
        assertTrue(all.get(0).getName().equals("zhangsan"));
    }

    /**
     *
     */
    @Configuration
    public static class MockUserRemoteService{

        @Bean
        public UserRemoteService userRemoteService(){
            UserRemoteService mock = Mockito.mock(UserRemoteService.class);
            User user = new User();
            user.setId(1L);
            user.setName("zhangsan");
            when(mock.findAll()).thenReturn(Arrays.asList(user));
            return mock;
        }
    }
}
