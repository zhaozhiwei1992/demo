package com.lx.demo.springbootjdbc.services;

import com.lx.demo.springbootjdbc.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class UserService {
    private final Map<String, User> data = new ConcurrentHashMap<>();

    Flux<User> list() {
        return Flux.fromIterable(this.data.values());
    }

    Flux<User> getById(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    Mono<User> createOrUpdate(final User user) {
        this.data.put(String.valueOf(user.getId()), user);
        return Mono.just(user);
    }


    Mono<User> delete(final String id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }
}

