package com.lx.demo;

import com.lx.demo.model.User;
import com.lx.demo.persistence.UserDAO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 2018/5/9.
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrmTest {


    @Autowired
    UserDAO userDao;


    @Test
    @Ignore
    public void test(){
        System.out.println(userDao);
    }


    @Test
    @Ignore
    public void testSelect(){

        try {
//            List<User> result = userDao.selectAll();
//            System.out.println(Arrays.toString(result.toArray()));

            List<User> result = userDao.selectByName("tom");
            System.out.println(Arrays.toString(result.toArray()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testInsert(){
        try {
            for (int age = 25; age < 35; age++) {
                User user = new User();
                user.setName("Tom");
                userDao.insert(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
