package com.lx.demo.springbootjdbc.webflux;

import com.lx.demo.springbootjdbc.domain.User;
import com.lx.demo.springbootjdbc.repository.UserRepository;
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

@Configuration
public class WebFluxConfiguration {

    /**
     *
     * @param userHandler
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> saveUser(UserHandler userHandler){
//        return route(POST("/webflux/user/save"), userHandler::save);
        return route(POST("/webflux/user/save"), serverRequest -> {
            Mono<User> userMono = serverRequest.bodyToMono(User.class);
//            return ServerResponse.ok().body(userMono.map(userRepository::insert), Boolean.class);
            return ServerResponse.ok().body(userMono.map(user -> {
                return userRepository.insert(user);
            }), Boolean.class);
        });
    }

    private UserRepository userRepository;

    public WebFluxConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @param
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> findAll(){
        return route(POST("/webflux/user/all"), (ServerRequest serverRequest)->{
            Collection<User> users = userRepository.findAll();
            Flux<User> userFlux = Flux.fromIterable(users);
            Mono<Flux<User>> just = Mono.just(userFlux);
            return ServerResponse.ok().body(userFlux, User.class);
        });
    }
}
