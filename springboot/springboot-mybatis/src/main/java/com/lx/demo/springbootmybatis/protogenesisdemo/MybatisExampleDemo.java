package com.lx.demo.springbootmybatis.protogenesisdemo;

import com.lx.demo.springbootmybatis.domain.User;
import com.lx.demo.springbootmybatis.domain.UserExample;
import com.lx.demo.springbootmybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

public class MybatisExampleDemo {

    public static void main(String[] args) throws IOException {
        //1. classloader
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        //2. 加载资源
//        Resource resource = defaultResourceLoader.getResource("mybatis-config.xml");
        InputStream resourceAsStream = contextClassLoader.getResourceAsStream("mybatis-config.xml");

        //3. 字符流
        InputStreamReader reader = new InputStreamReader(resourceAsStream, "UTF-8");
//        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
//        Reader reader = encodedResource.getReader();

        //4. 获取sqlsession
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory developmentSqlSessionFactory = sqlSessionFactoryBuilder.build(reader, "development", new Properties());
        SqlSession sqlSession = developmentSqlSessionFactory.openSession();

        //业务部分 begin
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(1);
        List<User> users = userMapper.selectByExample(userExample);
        users.stream().forEach(System.out::println);

        // end
        sqlSession.close();
        reader.close();
    }
}
