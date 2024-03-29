package com.example.springbootatomikos.services;

import com.example.springbootatomikos.domain.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.springbootatomikos.services
 * @Description: TODO
 * @date 2022/4/25 上午9:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends TestCase {

    @Autowired
    private PrimaryUserService primaryUserService;

    @Autowired
    private SecondaryUserService secondaryUserService;

    @Autowired
    private ExampleService exampleService;

    @Test
//    设置test默认不回滚
    @Rollback(value = false)
//    这里直接设置Transactional的方式不行，事物无法统一控制, 实现exampleService来处理
    @Transactional(rollbackOn = Exception.class)
    public void testSave() {

        exampleService.testSave();

//        for (int i = 0; i < 20; i++) {
//            final User user = new User();
//            user.setId(i);
//            user.setName("zhangsan");
//            user.setAge(18);
//
//            primaryUserService.save(user);
//            secondaryUserService.save(user);
//        }
    }

    public void testSaveAll() {
    }

    public void testDelete() {
    }

    public void testFindAll() {
    }
}