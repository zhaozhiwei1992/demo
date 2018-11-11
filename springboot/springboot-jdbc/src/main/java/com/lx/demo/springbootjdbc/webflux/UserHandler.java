package com.lx.demo.springbootjdbc.webflux;

import com.lx.demo.springbootjdbc.domain.User;
import com.lx.demo.springbootjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    /**
     *  // 在 Spring Web MVC 中使用 @RequestBody
     *         // 在 Spring Web Flux 使用 ServerRequest
     *         // Mono<User> 类似于 Optional<User>
     * @param serverRequest
     * @param <T>
     * @return
     */
    public <T extends ServerResponse> Mono<T> save(ServerRequest serverRequest) {
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        // map 相当于 转化工作
        Mono<Boolean> booleanMono = userMono.map(userRepository::insert);
        return (Mono<T>) ServerResponse.ok().body(booleanMono,Boolean.class);
    }
}
