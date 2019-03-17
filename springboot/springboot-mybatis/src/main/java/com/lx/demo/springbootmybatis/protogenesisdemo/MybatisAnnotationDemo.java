package com.lx.demo.springbootmybatis.protogenesisdemo;

import com.lx.demo.springbootmybatis.domain.User;
import com.lx.demo.springbootmybatis.mapper.UserMapper;
import com.lx.demo.springbootmybatis.mapper.UserMapperAnnotation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 注解方式使用usermapper
 */
public class MybatisAnnotationDemo {
    public static void main(String[] args) throws IOException {
        //1. classloader
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        //2. 加载资源
        InputStream resourceAsStream = contextClassLoader.getResourceAsStream("mybatis-config.xml");

        //3. 字符流
        InputStreamReader reader = new InputStreamReader(resourceAsStream, "UTF-8");

        //4. 获取sqlsession
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory developmentSqlSessionFactory = sqlSessionFactoryBuilder.build(reader, "development", new Properties());
        SqlSession sqlSession = developmentSqlSessionFactory.openSession();

        //业务部分 begin
        UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
        User user = mapper.selectByID(2);
        System.out.println(user);

        // end
        sqlSession.close();
        reader.close();
    }
}
