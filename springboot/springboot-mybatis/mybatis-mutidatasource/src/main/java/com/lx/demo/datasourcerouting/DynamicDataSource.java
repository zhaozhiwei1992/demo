package com.lx.demo.datasourcerouting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 主从数据源切换, 保存到threadLocal动态切换
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "master";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param dbType
     */
    public static void setDB(String dbType) {
        log.debug("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    /**
     *
     * 获取数据源名
     * @return
     */
    public static String getDB() {
        return (contextHolder.get());
    }

    /**
     *
     * 清除数据源名
     */
    public static void clearDB() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("数据源为{}", DynamicDataSource.getDB());

        return DynamicDataSource.getDB();
    }

}
