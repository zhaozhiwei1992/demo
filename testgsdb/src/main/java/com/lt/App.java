package com.lt;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        GetConnection("ifmis_pay_2", "ifmispay_2");
    }

    /**
     * 测试数据库连接
     * @param username
     * @param passwd
     * @return
     */
    public static Connection GetConnection(String username, String passwd)
    {
        //驱动类。
        String driver = "com.huawei.gauss.jdbc.ZenithDriver";
        //数据库连接描述符。
        String sourceURL = "jdbc:zenith:@192.168.100.243:1888";
        Connection conn = null;
        try {
            //加载数据库驱动。
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            //创建数据库连接。
            //getConnection(String url, String user, String password)
            conn = DriverManager.getConnection(sourceURL,username,passwd);
            System.out.println("Connection succeed!");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return conn;
    }
}
