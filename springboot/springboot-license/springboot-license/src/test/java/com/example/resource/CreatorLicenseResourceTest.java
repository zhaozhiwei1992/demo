package com.example.resource;

import cn.hutool.json.JSONUtil;
import com.example.domain.CustomLicenseParam;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit5 Test Class.java.java
 * @Package com.example.resource
 * @Description: 直接通过test方法，生成license文件
 * @date 2022/7/8 上午11:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties(CustomLicenseParam.class)
public class CreatorLicenseResourceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Autowired
    private CustomLicenseParam customLicenseParam;

    /**
     * @data: 2022/7/8-下午1:51
     * @User: zhaozhiwei
     * @method: generateLicense

     * @return: void
     * @Description:
     * 通过该方式直接请求rest接口生成license文件, 并且可以直接使用application.yml配置文件
     */
    @Test
    public void generateLicense() throws Exception {
        // 读取json文件
        System.out.println(customLicenseParam);
        // 调用post
        MvcResult result = mockMvc
                .perform(post("/license/generateLicense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJsonStr(customLicenseParam)))
                .andExpect(status().isOk())
                // 预期返回值的媒体类型text/plain;charset=UTF-8
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();// 返回执行请求的结果
    }
}