package com.example.springbootbean.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootbean.service
 * @Description: TODO
 * @date 2021/6/24 上午10:41
 */
@Service
public class AnimalService {

    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    private List<Animal> animalsBeans;

    /**
     * @data: 2021/6/24-上午10:50
     * @User: zhaozhiwei
     * @method: AnimalService
      * @param list : 一个接口有多个实现类，当注入的是这个接口的集合时，就不需要别名, 直接将实现类全部注入
     * @return:
     * @Description: 描述
     */
    public AnimalService(List<Animal> list) {
        logger.info("初始化animals {}", list);
        animalsBeans = list;
    }

    public List<Animal> animals(){
        return animalsBeans;
    }
}
