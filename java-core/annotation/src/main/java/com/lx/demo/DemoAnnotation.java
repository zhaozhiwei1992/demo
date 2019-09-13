package com.lx.demo;

import java.lang.annotation.*;

/**
 *  javap com.lx.demo.DemoAnnotation
 *
Compiled from "DemoAnnotation.java"
public interface com.lx.demo.DemoAnnotation extends java.lang.annotation.Annotation {
  public abstract java.lang.String value();
}

 注解最终就是个接口，　处理注解类或者方法是通过动态代理的方式
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DemoAnnotation {

    String value();

}