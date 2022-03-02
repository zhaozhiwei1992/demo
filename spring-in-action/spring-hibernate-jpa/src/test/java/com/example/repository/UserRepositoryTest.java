package com.example.repository;

import com.example.config.SystemConfig;
import com.example.domain.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.repository
 * @Description:
 *
 * @date 2022/3/1 下午2:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
@Transactional
public class UserRepositoryTest extends TestCase {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName(){
       testSave();
       User user = userRepository.findByName("zhangsan");
       Assert.notNull(user, "对象为空");
       Assert.isTrue(1 == user.getId(), "数据库数据与实际不符");

    }

    @Test
    public void testFindUsers() {
        testSave();
        final List<User> users = userRepository.findAll();
        System.out.println(users);
        Assert.isTrue(1 == users.size(), "应该为1条, 结果为" + users.size());
    }

    @Test
    public void testFindOne() {
        testSave();
        final User one = userRepository.findOne(1l);
        Assert.notNull(one, "对象为空");
        Assert.isTrue(1 == one.getId(), "数据库数据与实际不符");
    }

    @Test
    public void testSave() {
        final User user = new User();
        user.setId(1l);
        user.setName("zhangsan");
        user.setAge(18);
//        nested exception is javax.persistence.TransactionRequiredException: no transaction is in progress
//        final User save = userRepository.saveAndFlush(user);
        final User save = userRepository.save(user);
        Assert.notNull(save, "保存失败");
    }

    @Test
    public void testRepositoryExt(){
        userRepository.findUserExt();
    }
}