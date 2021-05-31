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
import java.util.Arrays;
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

        // 测试批量更新， 删除
        final User user1 = new User();
        user1.setId(3);
        final User user2 = new User();
        user2.setId(9);
        final int delete = sqlSession.delete("com.lx.demo.springbootmybatis.mapper.UserMapper.batchDeleteByUserId",
                Arrays.asList(user1, user2));
        System.out.println(delete);

        // 更新
        final User user3 = new User();
        user3.setId(1);
        user3.setName("33-1");
        user3.setRealname("admin33");

        final User user4 = new User();
        user4.setId(2);
        user4.setName("44");
        user4.setRealname("admin44");
        final int updateId = sqlSession.delete("com.lx.demo.springbootmybatis.mapper.UserMapper.batchUpdateByUserId",
                Arrays.asList(user3, user4));
        sqlSession.commit();
        System.out.println(updateId);

        // end
        sqlSession.close();
        reader.close();
    }
}
