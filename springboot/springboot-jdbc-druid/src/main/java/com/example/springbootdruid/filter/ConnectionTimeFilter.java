package com.example.springbootdruid.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;

import java.util.Properties;

public class ConnectionTimeFilter extends FilterEventAdapter {

    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        long startTime = System.currentTimeMillis();
        super.connection_connectBefore(chain, info);
        beginTime.set(startTime);
//        System.out.println("Connection created at: " + startTime);
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        super.connection_connectAfter(connection);
//        final long time = connection.getConnectedTime().getTime();
        System.out.println("druid获取连接耗时: " + (System.currentTimeMillis() - beginTime.get()));
    }
}