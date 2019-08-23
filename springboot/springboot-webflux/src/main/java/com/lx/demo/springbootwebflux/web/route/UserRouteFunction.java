package com.lx.demo.springbootwebflux.web.route;

import com.lx.demo.springbootwebflux.domain.User;
import com.lx.demo.springbootwebflux.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class UserRouteFunction {

    /**
     *curl -X POST -H Content-Type:application/json;charset=utf-8 -d {"id":1, "name":"xx3", "age":18} http
     * ://localhost:8080/webflux/user/save
     *
     * 等价restcontroller /users
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> saveUser(){
//        return route(POST("/webflux/user/save"), userHandler::save);
        return route(POST("/webflux/user/save"), serverRequest -> {
            Mono<User> userMono = serverRequest.bodyToMono(User.class);
            return ServerResponse.ok().body(userMono.map(user -> userService.save(user)), User.class);
        });
    }

    @Autowired
    private UserService userService;

    /**
     *
     * @param
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> findAll(){
        return route(POST("/webflux/user/all"), (ServerRequest serverRequest)->{
            Collection<User> users = userService.findAll();
            Flux<User> userFlux = Flux.fromIterable(users);
//            Mono<Flux<User>> just = Mono.just(userFlux);
            return ServerResponse.ok().body(userFlux, User.class);
        });
    }
}
