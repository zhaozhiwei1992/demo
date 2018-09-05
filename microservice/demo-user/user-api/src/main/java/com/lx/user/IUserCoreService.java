package com.lx.user;

import com.lx.user.dto.UserLoginRequest;
import com.lx.user.dto.UserLoginResponse;
import com.lx.user.dto.UserRegisterRequest;
import com.lx.user.dto.UserRegisterResponse;

public interface IUserCoreService {

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserRegisterResponse register(UserRegisterRequest request);
}
