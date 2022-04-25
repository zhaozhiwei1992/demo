package com.example.springbootatomikos.services;

import com.example.springbootatomikos.domain.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private UserService userService;

    @Test
    public void testSave() {
        final User user = new User();
        user.setName("zhangsan");
        user.setAge(18);
        userService.save(user);
    }

    public void testSaveAll() {
    }

    public void testDelete() {
    }

    public void testFindAll() {
    }
}