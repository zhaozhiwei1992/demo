package com.lx.demo.springbootpandect.web.controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.lx.demo.springbootpandect.domain.User;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Test
    public void contextLoads() {
    }

    // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。
    private MockMvc mockMvc;

    // 注入WebApplicationContext
    @Autowired
    private WebApplicationContext wac;

//    @Autowired
//    private MockHttpSession session;// 注入模拟的http session
//
//    @Autowired
//    private MockHttpServletRequest request;// 注入模拟的http request\

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void initUserByProperties() {
    }

    /**
     * curl -X POST -H Content-Type:application/json -d {"id":1, "name":"lisi", "age":18} http://localhost:8080/users
     */
    @Test
    public void save() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2L);
        map.put("name", "test");
        map.put("age", 50);

        /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
        MvcResult result = mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(map)))
                .andExpect(status().isOk())
                // 预期返回值的媒体类型text/plain;charset=UTF-8
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.age").value(50))
                .andReturn();// 返回执行请求的结果

//        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/users/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.age").value(50))
                .andReturn();// 返回执行请求的结果

    }

    @Test
    public void helloName() {
    }

    @Test
    public void hello() {
    }
}