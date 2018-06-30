package com.lx.demo;

/**
 * Hello world!
 *
 */
public class OracleDatabaseDriver implements DatabaseDriver
{
    public String connect(String host) {
        return "oracle connect " + host;
    }
}
