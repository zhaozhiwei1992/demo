package com.lx.demo.springbootjdbc.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

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
}
