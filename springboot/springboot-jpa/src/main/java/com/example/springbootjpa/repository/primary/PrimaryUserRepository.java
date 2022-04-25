package com.example.springbootjpa.repository.primary;

import com.example.springbootjpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Title: PrimaryUserRepository
 * @Package com/example/springbootatomikos/repository/primary/PrimaryUserRepository.java
 * @Description:
 *
 * 3. 分别创建主类和从类数据仓储
 * @author zhaozhiwei
 * @date 2022/4/25 上午10:23
 * @version V1.0
 */
@Repository
public interface PrimaryUserRepository extends JpaRepository<User, Long> {

    /**
     * 自定义sql
     * 在 SQL 的查询方法上面使用@Query注解，如涉及到删除和修改在需要加上@Modifying.也可以根据需要添加 @Transactional对事物的支持，查询超时的设置等。
     *
     * @return
     */
    @Query("select id, name, age from User")
    List<User> findBySql();

    @Modifying
    @Query("update User u set u.name = ?1 where u.id = ?2")
    int modifyByIdAndName(String name, Long id);

    @Modifying
    @Query("delete from User where id = ?1")
    void deleteByUserId(Long id);

    /**
     * @param name
     * @return
     */
    Optional<User> findByName(String name);

    Optional<User> findByIdOrName(Long id, String name);

    Page<User> findByName(String userName, Pageable pageable);

    User findFirstByOrderByNameAsc();

    Page<User> queryFirst10ByName(String name, Pageable pageable);

    List<User> findFirst10ByName(String name, Sort sort);

    List<User> findTop10ByName(String name, Pageable pageable);
}
