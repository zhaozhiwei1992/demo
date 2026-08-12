package com.acme.shop.user.web;

import com.acme.shop.user.api.UserApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** web 层只依赖 api 契约。 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserApi userApi;

    public UserController(UserApi userApi) {
        this.userApi = userApi;
    }

    @GetMapping("/me")
    public String me() {
        return userApi.currentUser();
    }
}
