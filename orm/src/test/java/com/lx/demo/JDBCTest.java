package com.lx.demo;

import com.lx.demo.model.User;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class JDBCTest
        extends TestCase {

    /**
     * 传统jdbc操作
     */
    public void testNormalJDBC() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=UTF-8&rewriteBatchedStatements=true","root","root");
            //创建Statement\PreparedStatement对象：
            preparedStatement = conn.prepareStatement("select id, name, password from t_user");

            List<User> users = new ArrayList<User>();
            //获取结果集
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }

            System.out.println(users);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 传统jdbc加反射, 动态设置字段, 这种情况下字段不需要set方法， 直接硬灌
     */
    public void testNormalJDBCReflect() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=UTF-8&rewriteBatchedStatements=true","root","root");
            //创建Statement\PreparedStatement对象：
            preparedStatement = conn.prepareStatement("select id, name, password from t_user");

            List<User> users = new ArrayList<User>();

            //获取类的属性
            Class<?> aClass = User.class;
            Field[] fields4User = aClass.getDeclaredFields();

            //获取结果集
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = (User) aClass.newInstance();
                for (Field field : fields4User) {
                    field.setAccessible(true);
//                    if(field.getType() == int.class) {
//                        field.set(user, resultSet.getInt(field.getName()));
//                    }if(field.getType() == String.class){
//                        field.set(user, resultSet.getString(field.getName()));
//                    }
                    field.set(user, resultSet.getObject(field.getName()));
                }
                users.add(user);
            }

            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JDBCTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(JDBCTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}
