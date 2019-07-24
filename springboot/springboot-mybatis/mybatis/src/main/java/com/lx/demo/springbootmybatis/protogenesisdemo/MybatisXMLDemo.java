package com.lx.demo.springbootmybatis.protogenesisdemo;

import com.lx.demo.springbootmybatis.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * 使用mybatis原生方式查询数据
 */
public class MybatisXMLDemo {
    public static void main(String[] args) throws Exception {
        //1. classloader
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();

        //2. 加载资源
        Resource resource = defaultResourceLoader.getResource("mybatis-config.xml");

        //3. 字符流
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        Reader reader = encodedResource.getReader();

        //4. 获取sqlsession
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory developmentSqlSessionFactory = sqlSessionFactoryBuilder.build(reader, "development", new Properties());
        SqlSession sqlSession = developmentSqlSessionFactory.openSession();

        //业务部分 begin
        User user = sqlSession.selectOne("com.lx.demo.springbootmybatis.mapper.UserMapper.selectByPrimaryKey", 1);
        System.out.println(user);

        // end
        sqlSession.close();
        reader.close();
    }
}
