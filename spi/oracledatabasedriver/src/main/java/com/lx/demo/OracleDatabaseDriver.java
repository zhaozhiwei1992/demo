package com.lx.demo;

/**
 * Hello world!
 *
 */
public class OracleDatabaseDriver implements DatabaseDriver
{
    @Override
    public String connect(String host) {
        return "oracle connect " + host;
    }
}
