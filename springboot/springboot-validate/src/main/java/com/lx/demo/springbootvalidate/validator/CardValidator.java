package com.lx.demo.springbootvalidate.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Card;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class CardValidator implements ConstraintValidator<Card, String> {
    @Override
    public void initialize(Card constraintAnnotation) {

    }

    Pattern compile = Pattern.compile("[0-9]{6}");
    /**
     *  需求: 传入卡号形式必须是 LX-6位数字
     *  curl -H "Accept:application/json" -H "Content-Type:application/json" -X POST -d '{"id":1,"name":"lisi","email":"11@qq.com","phone":"18234837162","cardNum":"LX-123456"}' http://127.0.0.1:8080/user/save
     * @param value 传入数据
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

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
