package com.lx.web.controller;

import com.lx.user.IUserCoreService;
import com.lx.user.dto.UserLoginRequest;
import com.lx.user.dto.UserLoginResponse;
import com.lx.user.dto.UserRegisterRequest;
import com.lx.user.dto.UserRegisterResponse;
import com.lx.web.controller.support.ResponseData;
import com.lx.web.controller.support.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 赵志伟
 * @ClassName: UserController
 * @description [在Controller上标注了@RestController，这样相当于Controller的所有方法都标注了@ResponseBody]
 * @create 2018-07-07 下午12:35
 **/
@RestController
public class UserController {
    @Autowired
    IUserCoreService userCoreService;

    @PostMapping("/login")
    public ResponseEntity doLogin(String username, String password,
                                  HttpServletResponse response){
        UserLoginRequest request=new UserLoginRequest();
        request.setPassword(password);
        request.setUserName(username);
        UserLoginResponse userLoginResponse=userCoreService.login(request);
        response.addHeader("Set-Cookie",
                "access_token="+userLoginResponse.getToken()+";Path=/;HttpOnly");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseData register(String username, String password, String mobile){
        ResponseData data=new ResponseData();

        UserRegisterRequest request=new UserRegisterRequest();
        request.setMobile(mobile);
        request.setUsername(username);
        request.setPassword(password);
        try {
            UserRegisterResponse response = userCoreService.register(request);
            data.setMessage(response.getMsg());
            data.setCode(response.getCode());
        }catch(Exception e) {
            data.setMessage(ResponseEnum.FAILED.getMsg());
            data.setCode(ResponseEnum.FAILED.getCode());
        }
        return data;
    }
}
