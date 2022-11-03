package com.example.springboottest.web;

import com.example.springboottest.configuration.UserConfiguration;
import com.example.springboottest.domain.User;
import com.example.springboottest.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
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
@RunWith(MockitoJUnitRunner.class)
public class MockitoUserControllerTest {

    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Test
    public void findOne() throws Exception {

        final User user = new User();
        user.setId(1);
        // mock参数设置
        when(userRepository.findOne()).thenReturn(user);

        // 发起请求
        mockMvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andDo(print());
                .andReturn();
    }
}