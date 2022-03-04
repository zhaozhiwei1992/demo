package com.example.repository;

import com.example.config.SystemConfig;
import com.example.domain.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/3/3 下午2:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class UserRepositoryTest {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Test
    public void testFindByName() {
        testSave();
        final User zhangsan = redisTemplate.opsForValue().get("zhangsan");
        Assert.notNull(zhangsan, "对象为空");
        Assert.isTrue("zhangsan".endsWith(zhangsan.getName()), "未找到name是zhangsan的对象");
    }

    @Test
    public void testSave() {
        final User zhangsan = new User(1, "zhangsan");
        redisTemplate.opsForValue().set(zhangsan.getName(), zhangsan);
    }

    @Test
    public void testRemove(){
        redisTemplate.opsForValue().set("zhangsan", null);
        final User zhangsan = redisTemplate.opsForValue().get("zhangsan");
        Assert.isNull(zhangsan, "对象不为空");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByNameCache(){
        User user =  userRepository.findByName("zhangsan");
        Assert.notNull(user, "对象为空");
        Assert.isTrue("zhangsan".endsWith(user.getName()), "未找到name是zhangsan的对象");
    }
}