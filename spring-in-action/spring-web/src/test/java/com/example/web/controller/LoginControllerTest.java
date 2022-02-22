package com.example.web.controller;

import com.example.config.SystemConfig;
import com.example.domain.User;
import com.example.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.web.controller
 * @Description: TODO
 * @date 2022/2/22 上午11:36
 */

public class LoginControllerTest extends TestCase {

    @Test
    public void testShowRegistrationForm() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(view().name("registerForm"));
    }

    @Test
    public void testProcessResgistration() throws Exception {
        // 1. 构建repository
        final User user = new User(1, "ttang");
        final UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(user)).thenReturn(user);

        // 2. 构建controller
        final LoginController loginController = new LoginController();
        loginController.setUserRepository(userRepository);
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

        // 3. mock controller
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .param("id", String.valueOf(user.getId()))
                .param("name", user.getName()))
//                使用redirectedUrl, controller返回值必须是"redirect:xx"
                .andExpect(redirectedUrl("/user/"+user.getId()));

//        校验表单保存, 这里一定要注意equals hashcode方法，否则不能通过校验
        verify(userRepository, atLeastOnce()).save(user);
    }
}