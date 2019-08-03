package com.example.service;

import com.example.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

/**
 * 数据聚合服务，一次请求整合各个微服务数据
 */
@Slf4j
@Service
public class AggregationService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserByID(Long id){
        // 创建一个被观察者, 主题
        return Observable.create(observer ->{
            final User user = restTemplate.getForObject("http://user-service-client/users/{id}", User.class, id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserByIDCopy(Long id){
        // 创建一个被观察者, 主题
        return Observable.create(observer ->{
            final User user = restTemplate.getForObject("http://user-service-client/users/{id}", User.class, id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    public User fallback(Long id){
        final User user = new User();
        user.setId(0L);
        user.setName("");
        user.setAge(0);
        return user;
    }
}
