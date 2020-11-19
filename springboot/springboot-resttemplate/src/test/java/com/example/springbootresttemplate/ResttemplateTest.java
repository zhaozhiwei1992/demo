package com.example.springbootresttemplate;

import com.example.springbootresttemplate.domain.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResttemplateTest {

    public static void main(String[] args) {

//        参数
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("param1", "我是param1");
        hashMap.put("param2", "我是param2");

        RestTemplate restTemplate = new RestTemplate();

        final List<Map> maps = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "id1");
        map1.put("name", "name1");
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("id", "id2");
        map2.put("name", "name2");
        maps.add(map1);
        maps.add(map2);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //使用httpEntity的方式比较灵活，里面可以包装map, javabean,字符串等序列化对象
        final HttpEntity<List<Map>> userHttpEntity = new HttpEntity<>(maps, httpHeaders);
        final ResponseEntity<List> userResponseEntity = restTemplate.postForEntity("http://127.0.0.1:8080/echo/mutiparam?param1={param1}&param2={param2}", userHttpEntity,
                List.class, hashMap);

        final List body = userResponseEntity.getBody();
        System.out.println(body);

    }
}
