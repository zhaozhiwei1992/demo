package com.lx.demo.readinglist;

import com.lx.demo.readinglist.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(
//        classes=AddressBookConfiguration.class)
//@WebAppConfiguration
//开启web上下文测试
@SpringBootTest(classes = ReadingListApplication.class)
public class MockMvcWebTests {

    //注入WebApplicationContext
    @Autowired
    private WebApplicationContext webContext;
    private MockMvc mockMvc;

    /**
     * MockMvc
     */
    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .build();
    }

    @Test
    public void homePage() throws Exception {
        mockMvc.perform(get("/readingList")) //get请求
                .andExpect(status().isOk()) //判断请求处理成功
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books")) //断定模型包含一个名为 books 的属性
                .andExpect(model().attribute("books",
                        is(empty()))); //属性是一个空集合
    }

    @Test
    public void postBook() throws Exception {
        //提交一个书添加请求
        mockMvc.perform(post("/readingList") //post请求
                //必须确保内容类型（通过 MediaType.APPLICATION_FORM_URLENCODED ）设置为application/x-www-form-urlencoded，这才是运行应用程序时浏览器会发送的内容类型
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "BOOK TITLE") //要用 MockMvcRequestBuilders 的 param 方法设置表单域
                .param("author", "BOOK AUTHOR")
                .param("isbn", "1234567890")
                .param("description", "DESCRIPTION"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList"));

        //创建实体对象
        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader("readingList");
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("DESCRIPTION");

        //获取现有图书判断是否存在上面创建的图书
        mockMvc.perform(get("/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook)))
                );
    }
}
