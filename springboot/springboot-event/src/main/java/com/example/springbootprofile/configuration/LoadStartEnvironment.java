package com.example.springbootprofile.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * {@link org.springframework.boot.ApplicationRunner}
 * {@link org.springframework.boot.CommandLineRunner}
 */
public class LoadStartEnvironment implements SpringApplicationRunListener {
    private static final Logger logger = LoggerFactory.getLogger(LoadStartEnvironment.class);

    private static boolean init = false;

    public LoadStartEnvironment(SpringApplication application, String[] args) {

    }
    /**
     * Called immediately when the run method has first started. Can be used for very
     * early initialization.
     */
    @Override
    public void starting() {
//    	System.out.println(SystemEnvironment.getProperty("server.port"));
//    	System.out.println("starting");

    }

    public static String microServiceName = null;

    /**
     * Called once the environment has been prepared, but before the
     * {link ApplicationContext} has been created.
     *
     * @param environment the environment
     */
    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        if(init) {
            return ;
        }
        Map<String, Object> envMap = environment.getSystemProperties();
        envMap.put("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH","true");
        envMap.put("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}");
        try {
//            if (StringUtils.isEmpty(SystemEnvironment.getProperty("CONFIG_SERVER"))) {
            final String[] activeProfiles = environment.getActiveProfiles();
            logger.info("当前加载的profile配置 {}", activeProfiles);
                Properties envProperties = new Properties();
                String[] files = new String[]{".env"};
                for (String filename : files) {
                    File file = ResourceUtils.getFile("classpath:"+filename);
                    if (file.exists()) {
                        logger.info("**加载初始化配置文件：" + file.getAbsolutePath());
                        java.io.InputStream ins = new FileInputStream(file);
                        envProperties.load(ins);
                        Enumeration en = envProperties.keys();
                        while (en.hasMoreElements()) {
                            String key = en.nextElement().toString();
                            String value = envProperties.getProperty(key);
                            envMap.put(key, value);
                        }
                        break;
                    }
                }
//            }else{
//                logger.info("已使用了env_file配置文件！" );
//            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        init = true;
    }

    /**
     * Called once the {link ApplicationContext} has been created and prepared, but
     * before sources have been loaded.
     *
     * @param context the application context
     */
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        logger.debug("加载初始化配置 contextPrepared：...");
    }

    /**
     * Called once the application context has been loaded but before it has been
     * refreshed.
     *
     * @param context the application context
     */
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        logger.debug("加载初始化配置 contextLoaded：...");
        String port = context.getEnvironment().getProperty("server.port");
        if (port != null) {
            microServiceName = context.getEnvironment().getProperty("spring.application.name");
        }
    }

    /**
     * The context has been refreshed and the application has started but
     * {link CommandLineRunner CommandLineRunners} and {link ApplicationRunner
     * ApplicationRunners} have not been called.
     *
     * @param context the application context.
     * @since 2.0.0
     */
    @Override
    public void started(ConfigurableApplicationContext context) {
        logger.debug("加载初始化配置 started：...");
    }

    /**
     * Called immediately before the run method finishes, when the application context has
     * been refreshed and all {link CommandLineRunner CommandLineRunners} and
     * {link ApplicationRunner ApplicationRunners} have been called.
     *
     * @param context the application context.
     * @since 2.0.0
     */
    @Override
    public void running(ConfigurableApplicationContext context) {
        logger.debug("加载初始化配置 running：...");
    }

    /**
     * Called when a failure occurs when running the application.
     *
     * @param context   the application context or {@code null} if a failure occurred before
     *                  the context was created
     * @param exception the failure
     * @since 2.0.0
     */
    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        logger.error("加载初始化配置 failed：...");
    }
}
