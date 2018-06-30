package com.lx.demo;

/**
 * 数据库驱动接口规范
 *
 */
public interface DatabaseDriver
{
    String connect(String host);
}
