package com.lx.demo.springbootvalidate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 直接校验参数，不需要通过实体bean方式传参数
 * {@see https://blog.csdn.net/u013815546/article/details/77248003}
 *JSR提供的校验注解：
 * @Null 被注释的元素必须为 null
 * @NotNull 被注释的元素必须不为 null
 * @AssertTrue 被注释的元素必须为 true
 * @AssertFalse 被注释的元素必须为 false
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
 * @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 * @Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式
 *
 *
 * Hibernate Validator提供的校验注解：
 * @NotBlank(message =)   验证字符串非null，且长度必须大于0
 * @Email 被注释的元素必须是电子邮箱地址
 * @Length(min=,max=) 被注释的字符串的大小必须在指定的范围内
 * @NotEmpty 被注释的字符串的必须非空
 * @Range(min=,max=,message=) 被注释的元素必须在合适的范围内
 *
 */
@RestController
@Validated
public class ValidateMethodController {

    private static final Logger logger = LoggerFactory.getLogger(ValidateMethodController.class);

    /**
     * 一大波参数
     *
     * 測試:
     * curl -X GET http://127.0.0.1:8080/manyParam/2019?admdiv=150
     * {
     *   errorCode: 500
     * }
     * @param admdiv 数字不能有特殊字符，不能为空
     * @param year 年度 四位数字
     * @param data 不能为空
     */
    @GetMapping("/manyParam/{year}")
    public void manyParam(@Pattern (regexp = "^[0-9]{4}$", message = "請傳入正確區劃") @PathParam("admdiv") String admdiv,
                          @NotNull @PathVariable("year") Integer year,
                          @PathParam("data") String data){
        logger.info("传入参数成功, admdiv={}, year={}, data={}",admdiv, year, data);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Map handleConstraintViolationException(ConstraintViolationException cve){
        Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : cves) {
            logger.error(constraintViolation.getMessage());
        }
        Map map = new HashMap();
        map.put("errorCode",500);
        return map;
    }
}
