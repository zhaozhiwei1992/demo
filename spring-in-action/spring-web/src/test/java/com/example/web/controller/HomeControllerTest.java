package com.example.web.controller;

import com.example.config.SystemConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.web.controller
 * @Description: 通过junit测试home
 * @date 2022/2/21 上午9:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SystemConfig.class})
public class HomeControllerTest{

    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.view().name("home"));
        mockMvc.perform(MockMvcRequestBuilders.get("/homePage")).andExpect(MockMvcResultMatchers.view().name("home"));
    }

}