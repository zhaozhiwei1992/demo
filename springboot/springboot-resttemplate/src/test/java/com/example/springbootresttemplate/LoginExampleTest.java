package com.example.springbootresttemplate;

import com.example.springbootresttemplate.web.vm.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootresttemplate
 * @Description:
 * 测试登录认证后访问接口的RestTemplate例子
 * {@see com.longtu.web.rest.ExampleResourceTest#testEcho} 人大联网融合中心测试
 *
 * get请求如何传递header参考下述代码即可, 使用exchange,
 * 也可以全局自定义resttemplate, 重写方法, 使用threadlocal动态传入token
 * https://stackoverflow.com/questions/21101250/sending-get-request-with-authentication-headers-using-resttemplate
 * @date 2022/7/29 下午4:45
 */
public class LoginExampleTest {

    public void testEcho() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();

        // 1. 登录
        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", "admin");
        paramMap.put("password", "admin");

        final ResponseEntity<ResponseData> loginResponseEntity = restTemplate.postForEntity("http://127.0.0" +
                ".1:8090/api/login?username={username}&password={password}", null, ResponseData.class, paramMap);

        final ResponseData loginResponseData = loginResponseEntity.getBody();

        // 2. 登录后带着认证信息访问别的接口
        //https://stackoverflow.com/questions/21101250/sending-get-request-with-authentication-headers-using-resttemplate
        String url = "http://127.0.0.1:8090/api/ext/examples/echo";
        final ResponseEntity<ResponseData> exchange = restTemplate.exchange(
                RequestEntity.get(new URI(url))
                        // header构建
                        .header(HttpHeaders.AUTHORIZATION, loginResponseData.getData().toString())
                        .build(), ResponseData.class);
        System.out.println(exchange);

    }

}
