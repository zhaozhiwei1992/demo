package com.lx.demo.lambda;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {


    public static void main(String[] args) {
        final CustomLogger customLogger = new CustomLogger(CustomLogger.class);
        customLogger.info("helloworld");
        customLogger.info(() -> "helloworld supplier");
    }

    private static Map<Class, CustomLogger> logs = new ConcurrentHashMap<Class, CustomLogger>();

    public static CustomLogger getLogger(Class clazz) {
        if (!logs.containsKey(clazz)) {
            logs.put(clazz, new CustomLogger(clazz));
        }
        return logs.get(clazz);
    }

    private Logger logger;

    private CustomLogger(Class clazz) {
        this.logger = Logger.getLogger(clazz.getName());
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(Supplier<String> message) {
        // 能不能打印要看定义的打印级别和允许的打印级别比较
        if (logger.isLoggable(Level.INFO)) {
            logger.info(message.get());
        }
    }

    public void debug(String message) {
    }

    public void error(String message) {
    }

    public void error(String message, Throwable th) {
    }

}
