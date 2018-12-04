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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity doLogin(@RequestBody UserLoginRequest userLoginRequest,
                                  HttpServletResponse response){
        UserLoginResponse userLoginResponse=userCoreService.login(userLoginRequest);
        response.addHeader("Set-Cookie",
                "access_token="+userLoginResponse.getToken()+";Path=/;HttpOnly");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseData register(@RequestBody UserRegisterRequest userRegisterRequest){
        ResponseData data=new ResponseData();

        try {
            UserRegisterResponse response = userCoreService.register(userRegisterRequest);
            data.setMessage(response.getMsg());
            data.setCode(response.getCode());
        }catch(Exception e) {
            data.setMessage(ResponseEnum.FAILED.getMsg());
            data.setCode(ResponseEnum.FAILED.getCode());
        }
        return data;
    }
}
