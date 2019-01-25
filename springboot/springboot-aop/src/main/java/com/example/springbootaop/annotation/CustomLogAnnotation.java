package com.example.springbootaop.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CustomLogAnnotation {
    String value() default "我是默认值";
}
