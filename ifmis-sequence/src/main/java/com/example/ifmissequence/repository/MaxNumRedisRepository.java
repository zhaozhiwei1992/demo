package com.example.ifmissequence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.repository
 * @Description: 初始化redis记录, 构造最大号码表形式, 这里简化为agency_code: max_num
 * @date 2021/10/20 上午11:12
 */
@Component
public class MaxNumRedisRepository {

    private RedisTemplate redisTemplate;

    public MaxNumRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        // 初始化redis记录
        redisTemplate.opsForValue().set("30101", 1L);
        redisTemplate.opsForValue().set("30102", 9L);
        redisTemplate.opsForValue().set("30103", 11L);
    }

    public List<Map<String, Object>> findAll(){
        return null;
    }

    /**
     * @Description: 更新最大值
     */
    public boolean updateMaxNum(String agency_code, Integer maxNum){
        redisTemplate.opsForValue().set(agency_code, maxNum);
        return true;
    }

    /**
     * @data: 2021/10/20-上午10:58
     * @User: zhaozhiwei
     * @method: getMaxNum
     * @param agency_code :
     * @return: int
     * @Description: 实际生成过程中可以通过for update nowait来控制只能单个获取, 事务提交后释放资源
     * io.lettuce.core.RedisCommandExecutionException: ERR value is not an integer or out of rang
     */
    public int getMaxNum(String agency_code){
        return redisTemplate.opsForValue().increment(agency_code, 1).intValue();
    }
}
