package com.example.springbootresttemplate.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit5 Test Class.java.java
 * @Package com.example.springbootresttemplate.web.controller
 * @Description: TODO
 * @date 2021/1/6 下午3:50
 */
class ArrayControllerTest {

    @Test
    public void test(){
        String[] args = new String[4];
        args[0]="电子安徽划款读取2302";
        args[1]="bank";
        args[2]="bank.ELQDRefundRead";
        args[3]="gov.mof.fasp2.bank.etimertask.EBankCommonTask#executeELQDRefundRead";
        executeTask(args);
    }
    /**
     * 执行定时任务
     *
     * @param args       参数
     * @return
     */
    public void executeTask(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("http://%s/buscommon/task/executeTask", "127.0.0.1:8080");
        Map result = null;
        try {

            //设置访问参数
            MultiValueMap params = new LinkedMultiValueMap();
            params.put("args", Arrays.asList(args));
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                    url,
                    params, Map.class);
            result = responseEntity.getBody();
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new HashMap<>();
            result.put("status","fail");
            result.put("message",e1.getMessage());
            //throw new RuntimeException(e1.getMessage());
        }
        System.out.println(result);
    }

}