package com.lx.user.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lx.user.IUserCoreService;
import com.lx.user.constants.ResponseCodeEnum;
import com.lx.user.constants.Constants;
import com.lx.user.dal.entity.User;
import com.lx.user.dal.persistence.UserMapper;
import com.lx.user.dto.*;
import com.lx.user.exception.ServiceException;
import com.lx.user.exception.ValidateException;
import com.lx.user.util.ExceptionUtil;
import com.lx.user.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵志伟
 * @ClassName: UserCoreServiceImpl
 * @description [描述该类的功能]
 * @create 2018-07-06 下午4:07
 **/
@Service("userCoreService")
public class UserCoreServiceImpl implements IUserCoreService {

    private static final Logger logger = LoggerFactory.getLogger(UserCoreServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 用户登录
     * @param request
     * @return
     */
    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        logger.info("login request:"+request);
        UserLoginResponse response=new UserLoginResponse();
        try {
            beforeValidate(request);
            User user=userMapper.getUserByUserName(request.getUserName());
            if(user==null||!user.getPassword().equals(request.getPassword())){
                response.setCode(ResponseCodeEnum.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(ResponseCodeEnum.USERORPASSWORD_ERRROR.getMsg());
                return response;
            }
            Map<String,Object> map=new HashMap<>();
            map.put("uid",user.getId());
            map.put("exp", DateTime.now().plusSeconds(40).toDate().getTime()/1000);

            response.setToken(JwtTokenUtils.generatorToken(map));

            response.setUid(user.getId());
            response.setAvatar(user.getAvatar());
            response.setCode(ResponseCodeEnum.SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error("login occur exception :"+e);
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("login response->"+response);
        }

        return response;
    }

    /**
     * 用户注册
     * curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":1,"email":"11@qq.co","mobile":"18224807218","username":"zhangsan", "password":11}' http://127.0.0.1:8080/register
     * @param request
     * @return
     */
    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        logger.info("begin UserCoreService.register,request:【"+request+"】");

        UserRegisterResponse response=new UserRegisterResponse();
        try{
            beforeRegisterValidate(request);

            User user=new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setMobile(request.getMobile());
            user.setSex(request.getSex());
            user.setStatus(Constants.NORMAL_USER_STATUS);
            user.setCreateTime(new Date());

            int effectRow=userMapper.insertSelective(user);
            if(effectRow>0){
                response.setCode(ResponseCodeEnum.SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
                return  response;
            }
            response.setCode(ResponseCodeEnum.SYSTEM_BUSY.getCode());
            response.setMsg(ResponseCodeEnum.SYSTEM_BUSY.getMsg());
            return  response;
        }catch (DuplicateKeyException e){
            //TODO 用户名重复
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("register response:【"+response+"】");
        }

        return response;
    }

    private void beforeValidateAuth(CheckAuthRequest request){
        if(request==null){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getToken())){
            throw new ValidateException("token信息为空");
        }
    }

    @Override
    public CheckAuthResponse validToken(CheckAuthRequest request) {
        CheckAuthResponse response=new CheckAuthResponse();
        try{
            beforeValidateAuth(request);

            Claims claims=JwtTokenUtils.phaseToken(request.getToken());
            response.setUid(claims.get("uid").toString());
            response.setCode(ResponseCodeEnum.SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());

        }catch (ExpiredJwtException e){
            logger.error("Expire :"+e);
            response.setCode(ResponseCodeEnum.TOKEN_EXPIRE.getCode());
            response.setMsg(ResponseCodeEnum.TOKEN_EXPIRE.getMsg());
        }catch (SignatureException e1){
            logger.error("SignatureException :"+e1);
            response.setCode(ResponseCodeEnum.SIGNATURE_ERROR.getCode());
            response.setMsg(ResponseCodeEnum.SIGNATURE_ERROR.getMsg());
        }catch (Exception e){
            logger.error("login occur exception :"+e);
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("response:"+response);
        }

        return response;
    }

    /**
     * 登录校验
     * @param request
     */
    private void beforeValidate(UserLoginRequest request){
        if(request==null){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUserName())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
    }

    /**
     * 注册校验, 感觉这个地方应该写活，改代码太苦逼了
     *
     * //todo 这里统一通过注解来控制
     * @param request
     */
    private void beforeRegisterValidate(UserRegisterRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
        if(StringUtils.isEmpty(request.getMobile())){
            throw new ValidateException("手机号为空");
        }
    }
}

