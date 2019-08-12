package com.lx.demo.springbootmongodbmulti.repository.secondary;

import com.lx.demo.springbootmongodbmulti.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 多数据源都是通过config来初始化内部属性
 */
@Repository
public interface UserRepositorySecondary extends MongoRepository<User, Long> {
}
