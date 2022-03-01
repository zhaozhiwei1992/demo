package com.example.repository;

import com.example.config.SystemConfig;
import com.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/3/1 上午9:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
@Transactional
public class JpaEntityManagerRepositoryTest{

    @Autowired
    private JpaEntityManagerRepository jpaEntityManagerRepository;


    public void testFindUsers() {
    }

    @Test
    public void testFindOne() {
        testAdd();
        final User one = jpaEntityManagerRepository.findOne(1l);
        Assert.notNull(one, "对象为空");
        Assert.isTrue(1 == one.getId(), "数据库数据与实际不符");
    }

    @Test
    public void testSave() {
        final User user = new User();
        user.setId(1l);
        user.setName("zhangsan");
        user.setAge(18);
        final User save = jpaEntityManagerRepository.save(user);
        Assert.notNull(save, "保存失败");
    }

    @Test
//    @Transactional
    public void testAdd() {
        final User user = new User();
        user.setId(1l);
        user.setName("zhangsan");
        user.setAge(18);
        jpaEntityManagerRepository.add(user);
        jpaEntityManagerRepository.add(user);
    }
}