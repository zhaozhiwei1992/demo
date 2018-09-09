package com.lx.demo.junit.service.impl;

import com.lx.demo.junit.domain.User;
import com.lx.demo.junit.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockUserService implements UserService {

    private Map<Long, User> users = new ConcurrentHashMap();

    /**
     * 不存在时候返回null
     *
     * @return the previous value associated with <tt>key</tt>, or
     *      *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *      *         (A <tt>null</tt> return can also indicate that the map
     *      *         previously associated <tt>null</tt> with <tt>key</tt>,
     *      *         if the implementation supports <tt>null</tt> values.)
     * 第一次写入不存在返回null
     * 第二次写入时已经存在key
     * @param user
     * @return
     */
    @Override
    public boolean save(User user) {
        return users.put(user.getId(), user) == null;
    }
}
