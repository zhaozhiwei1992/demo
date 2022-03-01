package com.example.repository;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description:
 * Spring与Java持久化API
 * 基于JPA的应用程序需要使用EntityManagerFactory的实现类来获取EntityManager实例
 *
 * @Transactional表明这个Repository中的持久化方法是在事务上下文中执行的
 *
 * @date 2022/3/1 上午9:05
 */
@Repository
public class JpaEntityManagerRepository {

//    @PersistenceUnit
//    @PersistenceContext
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public List<User> findUsers(long startIndex, int Count) {
        return null;
    }

    public User findOne(long id) {
        return entityManagerFactory.createEntityManager().find(User.class, id);
    }

    public User save(User user) {
        return entityManagerFactory.createEntityManager().merge(user);
    }

    public void add(User user){
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(user);
//        https://stackoverflow.com/questions/13992230/getting-no-transaction-is-in-progress-in-spring-junit-test
//        entityManager.flush();
    }
}
