package com.lx.demo.springbootmongodbmulti.repository.primary;

import com.lx.demo.springbootmongodbmulti.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryPrimary extends MongoRepository<User, Long> {

}
