package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 创建一个结果集的接口来接收连表查询后的结果
     * @return
     */
    @Query("select t1.name as username, t2.name as bookname from User t1 left join Book t2 on t1.id = t2.id")
    Page<UserBookDTO> findByUser(Pageable pageable);

    /**
     * 自定义sql
     * 在 SQL 的查询方法上面使用@Query注解，如涉及到删除和修改在需要加上@Modifying.也可以根据需要添加 @Transactional对事物的支持，查询超时的设置等。
     * @return
     */
    @Query("select id, name, age from User")
    List<User> findBySql();

    @Modifying
    @Query("update User u set u.name = ?1 where u.id = ?2")
    int modifyByIdAndName(String  name, Long id);

    @Transactional
    @Modifying
    @Query("delete from User where id = ?1")
    void deleteByUserId(Long id);

    /**
     *
     * @param name
     * @return
     */
    Optional<User> findByName(String name);

    Optional<User> findByIdOrName(Long id, String name);

    /**
     * 复杂sql, 带分页
     * @param pageable
     * @return
     */
//    Page<User> findALL(Pageable pageable);

    Page<User> findByName(String userName, Pageable pageable);

    /**
     *
     *  有时候我们只需要查询前N个元素，或者支取前一个实体。
     * @return
     */
    User findFirstByOrderByNameAsc();

//    User findTopByOrderByAgeDesc();

    Page<User> queryFirst10ByName(String name, Pageable pageable);

    List<User> findFirst10ByName(String name, Sort sort);

    List<User> findTop10ByName(String name, Pageable pageable);
}
