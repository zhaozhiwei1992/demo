package com.example.springbootsession.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 测试redis key失效
 */
@RestController
public class ExpireController {

    private static final Logger logger = LoggerFactory.getLogger(ExpireController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/expire/{key}")
    public String expire(@PathVariable String key) throws InterruptedException {
        // 延时
//        if(time != null){
//            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
//        }
        if(hasKey(key)){
            logger.info("key {} 仍然存在, 值{}", key, get(key));
        }else{
            logger.info("key {} 不存在", key);

            //先设置值，才可以根据key指定失效时间
            set("xx", "hello");
            expire(key, 3);
        }
        return "操作成功";
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
