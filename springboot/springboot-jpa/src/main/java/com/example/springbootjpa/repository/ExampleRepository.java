package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.User;
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
        Query query = getSession().createSQLQuery(sql).setResultTransformer(HibernateAliasToEntityMapResultTransformer.INSTANCE);
        final List<Map> resultList = query.list();

        System.out.println(resultList);
        return resultList.toString();
    }

    /**
     * @data: 2021/9/22-下午8:02
     * @User: zhaozhiwei
     * @method: saveOrUpdate
      * @param user : 
     * @return: java.lang.String
     * @Description:
     * hibernate的saveOrUpdate，返回void，而merge是返回一个对象。
     * saveOrUpdate，会根据id判断是否持久化过，来Save或者update。之后对象就成为持久化状态。
     * 而merge只是将对象保存到数据库，并没有成为持久化状态。
     * jpa的persist ，必须配合@version 版本来使用。如果没有而直接persist，那么会报detached entity pass to persist。这个异常一般是id生成策略的问题。
     */
    public String saveOrUpdate(User user){
        getSession().saveOrUpdate(user);
        return "success";
    }

    /**
     * @data: 2021/9/22-下午8:10
     * @User: zhaozhiwei
     * @method: merge
      * @param user :
     * @return: java.lang.String
     * @Description: 基本等价hibernate中saveorupdate
     */
    public String merge(User user){
        final User merge = entityManager.merge(user);
        System.out.println(merge);
        return "success";
    }
}
