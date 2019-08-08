package com.example.springbootjpa.repository;

/**
 *创建一个结果集的接口来接收连表查询后的结果
 */
public interface UserBookDTO {
    String getUserName();
    String getBookName();
}
