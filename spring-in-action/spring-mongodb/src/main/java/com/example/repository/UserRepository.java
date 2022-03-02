package com.example.repository;

import com.example.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.repository
 * @Description: TODO
 * @date 2022/3/2 上午9:42
 */
public interface UserRepository extends MongoRepository<Order, Long> {
}
