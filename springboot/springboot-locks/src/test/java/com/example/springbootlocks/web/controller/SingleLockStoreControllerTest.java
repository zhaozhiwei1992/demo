package com.example.springbootlocks.web.controller;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.springbootlocks.web.controller
 * @Description: TODO
 * @date 2021/10/25 下午4:32
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SingleLockStoreController.class)
public class SingleLockStoreControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lock"))
                .andExpect(status().isOk())
//                请求结果
                .andExpect(content().string("success"));

        mockMvc.perform(MockMvcRequestBuilders.get("/lock"))
                .andExpect(status().isOk())
//                请求结果
                .andExpect(content().string("fail"));

//        验证1分钟后可以重新获得锁
        Thread.sleep(1000*60);

        mockMvc.perform(MockMvcRequestBuilders.get("/lock"))
                .andExpect(status().isOk())
//                请求结果
                .andExpect(content().string("success"));
    }

    @Test
    public void testReleaseLock() throws Exception {
//        释放锁
        mockMvc.perform(MockMvcRequestBuilders.get("/release"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/lock"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

}