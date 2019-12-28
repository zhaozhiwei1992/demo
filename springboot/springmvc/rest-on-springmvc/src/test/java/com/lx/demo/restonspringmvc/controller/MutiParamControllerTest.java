package com.lx.demo.restonspringmvc.controller;

import com.lx.demo.restonspringmvc.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MutiParamControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        //初始化mock
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void mutiParam() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                post("/mutiparam/{id}", 1)
                        .param("name", "zhangsan")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("xx")))//判断返回文本
                .andDo(print())//输出测试的报告
                .andReturn();//返回请求结果
    }

    /**
     * 使用resttemplate
     */
    @Test
    public void mutiParam2() {
        final RestTemplate restTemplate = new RestTemplate();

        //请求url
        String url = "http://localhost:8080/mutiparam2";
        //创建请求头
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        final Person person = new Person();
        person.setId(1);
        person.setName("zhangsan");
        //创建请求体
        final HttpEntity<Person> httpEntity = new HttpEntity<>(person, httpHeaders);
        final String s = restTemplate.postForObject(url, httpEntity, String.class);
        System.out.println(s);

    }

    @Test
    public void mutiParam3() {
    }
}