package com.lx.demo.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Title: CustomRequestBodyAdvice
 * @Package com/lx/demo/interceptor/CustomRequestBodyAdvice.java
 * @Description: 通过该扩展点, 在请求前后做些特殊处理
 * @author zhaozhiwei
 * @date 2022/9/22 下午3:33
 * @version V1.0
 */
public class CustomRequestBodyAdvice extends RequestBodyAdviceAdapter {
    public CustomRequestBodyAdvice() {
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return false;
    }

    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> selectedConverterType) throws IOException {
        return inputMessage;
    }

}
