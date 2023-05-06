package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.exception.CustomException;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: JUnit3 Test Class.java.java
 * @Package: com.example.springbootjpa.service
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/5/6 上午9:58
 * @version: V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * @data: 2023/5/6-上午10:56
     * @User: zhaozhiwei
     * @method: testSave

     * @return: void
     * @Description: 普通保存, junit中Transactional注解会默认回滚， 如果不想回滚可以增加rollback注解, 特殊异常回滚
     * 或者Rollback(false) 死活不回滚
     */
    @Test
    @Transactional
//    @Rollback(false)
    public void testSave1() {
        final User user = new User();
//        user.setId(1L);
        user.setName("保存正常1条");
        user.setAge(18);
        //Rolled back transaction for test
        // junit事务成功后会自动回滚， 防止数据污染 np
        final User save = userService.save(user);

        // 线程内部保存也会跟着Transactional一起回滚
        new Thread(new Runnable() {
            @Override
            public void run() {
                user.setName("test1线程内部创建用户");
                userService.save(user);
            }
        }).start();
        Assert.assertEquals(true, save.getId() != null);
    }

    /**
     * @data: 2023/5/6-上午11:00
     * @User: zhaozhiwei
     * @method: testSave2

     * @return: void
     * @Description: 两条记录, 事务一致， 报错后全部回滚
     */
    @Test
    @Transactional
    public void testSave2() {
        {
            final User user = new User();
            user.setName("test2保存失败1");
            user.setAge(18);
            final User save = userService.save(user);
        }

        {
            final User user = new User();
            user.setName("test2保存失败2");
            user.setAge(18);
            final User save = userService.save(user);
        }

        if(1>0){
            throw new RuntimeException("事务回滚");
        }
        System.out.println("testsave2 保存失败执行完成, 数据库中应无记录");
    }

    /**
     * @data: 2023/5/6-上午11:07
     * @User: zhaozhiwei
     * @method: testSave3

     * @return: void
     * @Description: 一条应该成功 save2
     */
    @Test
    @Transactional
    public void testSave3() {
        {
            final User user = new User();
            user.setName("test3保存失败1");
            user.setAge(18);
            final User save = userService.save(user);
        }

        {
            final User user = new User();
            user.setName("test3失败1不影响2");
            user.setAge(18);
            // 存在嵌套事务，并且为_new
            // junit事务的回滚影响不到这个，说明使用_new就完全是个新的事务
            final User save = userService.save2(user);
        }

//        if(1>0){
//            throw new RuntimeException("事务回滚");
//        }
        System.out.println("testsave3 保存失败执行完成, 数据库增加一条记录");
    }

    /**
     * @data: 2023/5/6-下午2:28
     * @User: zhaozhiwei
     * @method: testAsyncSave

     * @return: void
     * @Description: 使用了async事务也会跟着外部的走
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAsyncSave() throws InterruptedException {
        {
            final User user = new User();
//        user.setId(1L);
            user.setName("async:1保存正常1条");
            user.setAge(18);
            //Rolled back transaction for test
            // junit事务成功后会自动回滚， 防止数据污染 np
            final User save = userService.save(user);
        }
        {
            final User user = new User();
//        user.setId(1L);
            user.setName("async:2保存正常1条");
            user.setAge(18);
            userService.asyncSave(user);
        }

        Thread.sleep(1000);

        System.out.println("保存成功");
    }
}