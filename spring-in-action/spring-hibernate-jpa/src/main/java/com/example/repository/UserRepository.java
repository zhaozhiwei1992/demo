package com.example.repository;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description:
 *
 * jpa需要打开transactionManager, 否则事务不生效
 * com.example.config.JpaEntityManagerConfig#transactionManager(javax.persistence.EntityManagerFactory)
 * @date 2022/2/21 上午11:05
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExt {

//    @Query("select t1.id, t1.name, t1.age, t1.password from User t1 ")
//    List<User> findUsers(long startIndex, int Count);

//    @Query("select t1.id, t1.name, t1.age, t1.password from User t1 where id = ?1")
//    User findOne(Long id);

//   扩展定义查询方法
    User findByName(String name);

//    User save(User user);

}
