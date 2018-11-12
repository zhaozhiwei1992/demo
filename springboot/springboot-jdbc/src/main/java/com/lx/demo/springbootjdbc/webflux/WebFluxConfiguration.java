package com.lx.demo.springbootjdbc.webflux;

import com.lx.demo.springbootjdbc.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
        return route(POST("/webflux/user/save"), userHandler::save);
    }

    /**
     *
     * @param
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> findAll(){
        return route(POST("/webflux/user/all"), (ServerRequest serverRequest)->{
            Mono<User> userMono = serverRequest.bodyToMono(User.class);
//            ServerResponse.ok().body()
            return null;
        });
    }
}
