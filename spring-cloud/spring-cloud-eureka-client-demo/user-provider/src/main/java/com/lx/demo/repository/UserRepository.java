package com.lx.demo.repository;

import com.lx.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link User 用户} 仓储
 */
@Repository
public class UserRepository {

    private ConcurrentMap<Long, User> repository =
            new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator =
            new AtomicLong(0);

    public Collection<User> findAll() {
        return repository.values();
    }

    public boolean save(User user) {
        Long id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.putIfAbsent(id, user) == null;
    }
}
