package com.lx.demo.junit.mock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

/**
 * 模拟servlet测试
 */
public class ServletAPITest {

    /**
     * 静态mock， 有默认的实现，可以写入值返回值进行测试
     */
    @Test
    public void testHttpServletAPIInStaticMock(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setParameter("name", "zhangsan");
        Assert.assertEquals("zhangsan", mockHttpServletRequest.getParameter("name"));
    }

    /**
     * 没有默认实现，只能生产代理对象
     */
    @Test
    public void testHttpServletAPIInDynamicMock(){
        HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
        when(mock.getParameter("name")).thenReturn("zhangsan");

        Assert.assertEquals("zhangsan", mock.getParameter("name"));
    }
}
