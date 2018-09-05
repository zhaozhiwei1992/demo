package com.lx.user;

import com.lx.user.dal.entity.User;
import com.lx.user.dal.persistence.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/application-common.xml","classpath*:META-INF/spring/application-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest
{
    @Autowired
    private SqlSessionFactory sqlSessionFactory = null;

    @Test
    public void loadById() {

        SqlSession sqlSession = null;
        try{
            sqlSession = sqlSessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectByPrimaryKey(1);
            System.out.println(user);
        }finally{
            assert sqlSession != null;
            sqlSession.close();
        }
    }
}
