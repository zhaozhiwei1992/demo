package com.example.springbootcustomannotation.annotation;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 模仿spring service注解,spring自动扫描
 * {@link ClassPathScanningCandidateComponentProvider}
 * A component provider that scans the classpath from a base package.
 * It then applies exclude and include filters to the resulting classes to find candidates.
 * org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#registerDefaultFilters()
 *
 * 这种方式么的卵用，　还不如直接用spring原生的
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CustomizeCompenent1 {

    String value() default "";
}
