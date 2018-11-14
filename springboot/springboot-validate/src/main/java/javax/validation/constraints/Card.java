package javax.validation.constraints;


import com.lx.demo.springbootvalidate.validator.CardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 参考: {@link Email}
 *
 *  加入国际化配置{@link ValidationMessages_zh_CN.properties}
 */
@Documented
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CardValidator.class})
public @interface Card {
    String message() default "{javax.validation.constraints.Card.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
