package com.lx.demo.springbootlog.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit5 Test Class.java.java
 * @Package com.lx.demo.springbootlog.web.rest
 * @Description: TODO
 * @date 2021/6/21 上午11:54
 */
public class IndexResourceTest {

    private static final Logger logger = LoggerFactory.getLogger(IndexResourceTest.class);

    public void logger(){
        final HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("msg", "hh");
        final ArrayList arrayList = new ArrayList();
        arrayList.add(objectObjectHashMap);
        logger.error("msg {}", objectObjectHashMap);
        logger.error("msg2 {}", arrayList);

        try{
           if(1 > 0){
               throw new RuntimeException("报错了");
           }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
       new IndexResourceTest().logger();
    }
}