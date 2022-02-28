package com.example.repository;

import com.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/2/28 上午11:20
 */
@Repository
//com.example.config.HibernateSessionConfig.transactionAutoProxy 使用了这个设置BeanNames可以自动代理
//@Transactional
public class HibernateUserRepository {

    @Autowired
    protected SessionFactory sessionFactory;

    public Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<User> findUsers(int startIndex, int Count) {
        CriteriaQuery<User> criteriaQuery = currentSession().getCriteriaBuilder().createQuery(User.class);
//criteriaQuery 对象可以添加各种查询条件和关联条件等等
        criteriaQuery.from(User.class);
        return currentSession().createQuery(criteriaQuery).getResultList();
//        5.1废弃
//        return currentSession().createCriteria(User.class).list();
    }

    public User findOne(long id) {
        return currentSession().get(User.class, id);
    }

    public User save(User user) {
        final Serializable id = currentSession().save(user);
        return new User((Long)id, user.getCreateTime());
    }
}
