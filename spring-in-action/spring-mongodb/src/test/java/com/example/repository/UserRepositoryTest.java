package com.example.repository;

import com.example.config.SystemConfig;
import com.example.domain.Order;
import com.mongodb.WriteResult;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/3/2 下午4:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class UserRepositoryTest{

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save(){
        final Order order = new Order(1l, "xx");
        order.setVersion(99);
        mongoOperations.save(order);
//        final Order save = userRepository.save(order);
//        Assert.isTrue(save.equals(order), "保存失败");

    }

    @Test
    public void count(){
        save();
//        final long order = mongoOperations.getCollection("order").count();
//        Assert.isTrue(order > 0, "缺失数据");
        final Order order = userRepository.findOne(1l);
        Assert.notNull(order, "缺失数据");
        delete();
    }

    @Test
    public void delete(){
        final Order order = new Order(1l, "xx");
        final WriteResult remove = mongoOperations.remove(order);
    }

    @Test
    public void findById(){
        save();
        final Order order = mongoOperations.findById(1l, Order.class);
        Assert.isTrue(order.getId() == 1l, "未找到id为1l数据");
        delete();
    }
}