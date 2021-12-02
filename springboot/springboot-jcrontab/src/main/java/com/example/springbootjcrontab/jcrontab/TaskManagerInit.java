
package com.example.springbootjcrontab.jcrontab;

import org.jcrontab.Crontab;
import org.jcrontab.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class TaskManagerInit implements ITaskManagerInit {

    private static final Logger logger = LoggerFactory.getLogger(TaskManagerInit.class);

    private static Crontab crontab = Crontab.getInstance();

    private static boolean inited = false;

    private static void process() {
        Properties propObj = new Properties();
        propObj.setProperty("org.jcrontab.data.datasource", "com.example.springbootjcrontab.jcrontab.data.JcrontabDataSource");
        propObj.setProperty("org.jcrontab.log.Logger", "com.example.springbootjcrontab.jcrontab.log.JdbcLogger");
        propObj.setProperty("org.jcrontab.Crontab.refreshFrequency", "1");

        try {

            crontab.init(propObj);
        } catch (Exception e) {
            Log.error(e.toString(), e);
        }
    }

    private static void shutdownHook() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                doStop();
            }
        });
    }

    private static void doStop() {
        System.out.print("Shutting down...");

        crontab.uninit(100);
        System.out.println("Stoped");
    }

    @Override
    public void init() {
        if(!inited) {
            inited = true;
            try {
                shutdownHook();

                System.out.print("Start Working...");
                process();
                System.out.println("OK");
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    @Override
    public void destroy() {
        try {
            shutdownHook();
        } catch (Exception e) {
            logger.error("Close task manager failed", e);
        }
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub
    }

    public static void restart2() {

        try {
            doStop();

            System.out.print("Restart Working?...");
            process();
            System.out.println("OK");
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}