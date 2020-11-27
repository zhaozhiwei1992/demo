package com.lx.demo.restonspringmvc.controller;

import com.lx.demo.restonspringmvc.model.Person;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRestControllerTest {

    @Test
    public void oa() {
        RestTemplate template = new RestTemplate();
        String url = "http://127.0.0.1:8080/bdg-server/oadata?admDivCode=43000000&year=2021";
//        final List<Person> list = new ArrayList<>();
//        final Person person = new Person();
//        person.setId(1);
//        person.setAge(18);
//        personArrayList.add(person);

        final List<Object> list = new ArrayList<>();
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", 1);
        hashMap.put("age", 18);
        list.add(hashMap);

// 1、使用postForObject请求接口
        String result = template.postForObject(url, list, String.class);
        System.out.println("result1==================" + result);
    }

}
