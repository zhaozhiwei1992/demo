package com.example.exception;

import com.example.domain.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.exception
 * @Description: TODO
 * @date 2022/3/7 下午3:11
 */
@ControllerAdvice
public class UserNotFoundExceptionHandler {

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> userNotFound(UserNotFoundException e){
//        final long id = e.getId();
//        final Error error = new Error("404001", "User id [" + id + "] not found!");
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

//    跟上述方式基本等价
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userNotFound(UserNotFoundException e){
        final long id = e.getId();
        final Error error = new Error("404001", "User id [" + id + "] not found!");
        return error;
    }
}
