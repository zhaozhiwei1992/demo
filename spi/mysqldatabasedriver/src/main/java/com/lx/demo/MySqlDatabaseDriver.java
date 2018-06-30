package com.lx.demo;

/**
 * Hello world!
 *
 */
public class MySqlDatabaseDriver implements DatabaseDriver
{
    public String connect(String host) {
        return "mysql connect " + host;
    }
}
