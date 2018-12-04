package com.lx.user;

import com.lx.user.dto.*;

public interface IUserCoreService {

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserRegisterResponse register(UserRegisterRequest request);

    /**
     * 校验过程
     * @param request
     * @return
     */
    CheckAuthResponse validToken(CheckAuthRequest request);
}
