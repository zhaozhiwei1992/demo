package com.example.springboottest.web;

import com.example.springboottest.UserConfiguration;
import com.example.springboottest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit5 Test Class.java.java
 * @Package com.example.springboottest.web
 * @Description: TODO
 * @date 2021/10/20 下午3:26
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import(UserConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name
    // 'userController': Unsatisfied dependency expressed through field 'userRepository'; nested exception is org
    // .springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.example
    // .springboottest.repository.UserRepository' available: expected at least 1 bean which qualifies as autowire
    // candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOne() throws Exception {
        // 发起请求
        mockMvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}