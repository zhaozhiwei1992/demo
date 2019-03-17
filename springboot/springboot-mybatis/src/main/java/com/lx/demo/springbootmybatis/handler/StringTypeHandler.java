package com.lx.demo.springbootmybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类型转换工具， 可以转换复杂对象
 */
public class StringTypeHandler implements TypeHandler {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {

    }

    /**
     * 查询处理
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        System.err.println(String.format("原来是%s, 我改成了 helloworld", string));
        return "helloworld";
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        System.out.println(String.format("原来是%s, 我改成了 helloworld", string));
        return "helloworld";
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
