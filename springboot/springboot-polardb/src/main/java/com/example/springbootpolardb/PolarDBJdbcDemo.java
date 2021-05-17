package com.example.springbootpolardb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * POLARDB JDBC DEMO
 * <p>
 * Please make sure the host ip running this demo is in you cluster's white list.
 */
public class PolarDBJdbcDemo {
    /**
     * Replace the following information.
     */
    private final String host = "10.249.160.207";
    private final String user = "csbdg_330000";
    private final String password = "1";
    private final String port = "3433";
    private final String database = "ysglyth";

    public void run() throws Exception {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.aliyun.polardb.Driver");

            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);
            String url = "jdbc:polardb://" + host + ":" + port + "/" + database;
            connect = DriverManager.getConnection(url, props);

            /**
             * create table foo(id int, name varchar(20));
             */
            String sql = "select * from act_hq_tem_def where deploy_state=1";
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(resultSet);
            while (resultSet.next()) {
                System.out.println("content_bytes" + toByteArray(resultSet.getBinaryStream(12)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        PolarDBJdbcDemo demo = new PolarDBJdbcDemo();
        demo.run();
    }
}