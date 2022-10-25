package com.example.springboottest.repository;

import com.example.springboottest.UserConfiguration;
import com.example.springboottest.domain.User;
import com.example.springboottest.web.UserController;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.springboottest.repository
 * @Description: 涉及到的bean需要引入才能autowired
 * 或者直接使用@SpringbootTest搞定
 * @date 2021/10/20 下午4:31
 */
@RunWith(SpringRunner.class)
@Import({UserConfiguration.class, UserRepository.class})
public class UserRepositoryTest extends TestCase {

    @Autowired
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne() {
        final User one = userRepository.findOne();
        Assert.assertEquals(one.getId(), user.getId());
    }
}