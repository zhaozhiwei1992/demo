package com.example.springbootjpa.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootjpa.repository
 * @Description: TODO
 * @date 2021/5/20 下午10:04
 */
@Repository
public class ExampleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * @data: 2021/5/20-下午10:39
     * @User: zhaozhiwei
     * @method: getSession

     * @return: org.hibernate.Session
     * @Description: Spring Boot 2 + JPA / Hibernate 5 注入 SessionFactory 的正确姿势
     * https://blog.csdn.net/qq_15329947/article/details/85232287
     *
     */
    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    public String query(){

        // 测试 false 是否会被转换成f, 结果原样显示
        String sql = "SELECT  'false' AS ISFREQUENTLYUSED, M.*  FROM SYS_MENU M";
//        final Query query = entityManager.createNativeQuery(sql);
//        final List<Map> resultList = query.getResultList();
        Query query = getSession().createSQLQuery(sql);
        final List<Map> resultList = query.list();

        System.out.println(resultList);
        return resultList.toString();
    }
}
