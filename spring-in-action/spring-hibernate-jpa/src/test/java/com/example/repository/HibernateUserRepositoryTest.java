package com.example.repository;

import com.example.config.SystemConfig;
import com.example.domain.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/2/28 下午2:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SystemConfig.class})
public class HibernateUserRepositoryTest extends TestCase {

    @Autowired
    private HibernateUserRepository hibernateUserRepository;

    @Test
    public void testFindUsers() {
        testSave();
        final List<User> users = hibernateUserRepository.findUsers(1, 20);
        Assert.isTrue(1 == users.size(), "应该为1条, 结果为" + users.size());
    }

    @Test
    public void testFindOne() {
        testSave();
        final User one = hibernateUserRepository.findOne(1l);
        Assert.notNull(one, "对象为空");
        Assert.isTrue(1 == one.getId(), "数据库数据与实际不符");
    }

    @Test
    public void testSave() {
        final User user = new User();
        user.setId(1l);
        user.setName("zhangsan");
        user.setAge(18);
        final User save = hibernateUserRepository.save(user);
        Assert.notNull(save, "保存失败");
    }
}