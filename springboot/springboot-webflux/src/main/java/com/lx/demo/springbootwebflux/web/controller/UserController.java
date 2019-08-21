package com.lx.demo.springbootwebflux.web.controller;

import com.lx.demo.springbootwebflux.domain.User;
import com.lx.demo.springbootwebflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 响应式编程的返回值必须是 Flux 或者 Mono ，两者之间可以相互转换。
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/hello")
    public Mono<String> hello(String name) {
        return Mono.just("hello: " + name);
    }

    private UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

//    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public void notFound() {
//    }

    /**
     * curl http://localhost:8080/users
     *
     * @return
     */
    @GetMapping("")
    public Flux<User> findAll() {
        return Flux.fromIterable(userService.findAll());
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") final Long id) {
        return Mono.justOrEmpty(this.userService.findById(id));
    }
//

    /**
     * curl -X POST -H Content-Type:application/json;charset=utf-8 -d {"id":1, "name":"lisi", "age":18} http
     * ://localhost:8080/users/
     *
     * @param user
     * @return
     */
    @PostMapping("")
    public Mono<User> create(@RequestBody final User user) {
        final User save = this.userService.save(user);
        return Mono.just(save);
    }

    /**
     *
     * curl http://localhost:8080/users/1
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<User>  update(@PathVariable("id") final Long id, @RequestBody final User user) {
        Objects.requireNonNull(user);
        user.setId(id);
        return Mono.just(this.userService.update(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id) {
        this.userService.delete(id);
    }
}
