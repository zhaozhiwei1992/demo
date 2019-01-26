package com.lx.demo.springbootvalidate.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Card;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 自定义校验实现, 参考 {@link EmailValidator}
 */
public class CardValidator implements ConstraintValidator<Card, String> {

    /**
     * 根据传入规则校验卡号信息, 注解传入参数
     */
    private String value;

    @Override
    public void initialize(Card constraintAnnotation) {
        System.out.println(constraintAnnotation.value());
        this.value = constraintAnnotation.value();
    }

    /**
     * 表达式做死的情况
     */
    Pattern compile = Pattern.compile("[0-9]{6}");

    /**
     *  需求: 传入卡号形式必须是 LX-6位数字
     *  curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":1,"name":"lisi","email":"11@qq.com","phone":"18234837162","cardNum":"LX-123456"}' http://127.0.0.1:8080/user/save
     *
     *  校验失败后就会 输出message信息
     * @param value 传入数据
     * @param constraintValidatorContext 包含了认证中所有的信息，可以利用这个上下文实现获取默认错误提示信息，禁用错误提示信息，改写错误提示信息等。
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        // 注解传入参数
        System.out.println(this.value);

        //重写默认提示消息
        //获取默认提示信息
        String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
        System.out.println("default message :" + defaultConstraintMessageTemplate);
        //禁用默认提示信息
        constraintValidatorContext.disableDefaultConstraintViolation();
        //设置提示语
        constraintValidatorContext.buildConstraintViolationWithTemplate("默认的被搞掉了{value}").addConstraintViolation();

        String[] strings = StringUtils.delimitedListToStringArray(value, "-");
        if(strings.length != 2){
            return false;
        }
        String prefix = strings[0];
        String suffix = strings[1];

        boolean isValidatedPrefix = Objects.equals(prefix, "LX");
        boolean isValidatedSuffix = compile.matcher(suffix).matches();
        return isValidatedPrefix && isValidatedSuffix;
    }
}
