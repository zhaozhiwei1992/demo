package com.example.fallback;

import com.example.api.UserService;
import com.example.domain.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 实现客户端回退并且可以获取异常，　实现hystrixcommand的效果，这里是不能在fallback方法加throwable参数的
 * 因为feign的回退类必须实现feign接口，参数列表就么得变化了
 */
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceFallbackFactory.class);

    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {

            @Override
            public boolean createUser(User user) {
                return false;
            }

            @Override
            public List<User> getAllUser() {
                // 日志放到fallback方法中不要放到create中
                logger.error("fallback! reason is: {}", throwable);
                return Collections.emptyList();
            }

            @Override
            public User findById(Long id) {
                return new User();
            }
        };
    }
}
