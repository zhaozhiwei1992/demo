package com.example.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.core.env.*;

import java.util.*;

/**
 * @author qiu
 */
public class SystemEnvironment{
    private final Log logger = LogFactory.getLog(getClass());

    public static Environment environment;

    public static boolean onlyprintconfig = false;

    private static Map<String, Object> environmentObject = new HashMap<>();

    private static PropertyResolver getPropertyResolver() {
        if (environment instanceof ConfigurableEnvironment) {
            PropertyResolver resolver = new PropertySourcesPropertyResolver(
                    ((ConfigurableEnvironment) environment).getPropertySources());
            ((PropertySourcesPropertyResolver) resolver)
                    .setIgnoreUnresolvableNestedPlaceholders(true);
            return resolver;
        }
        return environment;
    }


    public static  void initEnvironment(Environment environment){
        SystemEnvironment.environment = environment;
    }

    public static String getProperty(String name) {
        return getPropertyResolver().getProperty(name);
    }

    public static void setProperty(String name, String value) {
        if (System.getProperty(name) == null && value != null) {
            System.setProperty(name, value);
        }
    }

    public static Object getEnvObject(String name) {
        return environmentObject.get(name);
    }

    public static void setEnvObject(String name, Object value) {
        environmentObject.put(name,value);
    }


    /**
     * PropertySource 'configurationProperties'org.springframework.boot.context.properties.source.SpringConfigurationPropertySources
     * PropertySource 'bootstrap'java.util.HashMap
     * PropertySource 'systemProperties'java.util.Properties
     * PropertySource 'systemEnvironment'java.util.Collections$UnmodifiableMap
     * PropertySource 'random'java.util.Random
     * PropertySource 'springCloudClientHostInfo'java.util.LinkedHashMap
     * PropertySource 'applicationConfig: [classpath:/bootstrap.yml]'java.util.LinkedHashMap
     * PropertySource 'defaultProperties'java.util.HashMap
     */
    public static void printEnvironment( ){
        PropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        if (propertySources != null) {
            for (PropertySource<?> propertySource : propertySources) {
                System.out.println("PropertySource '" +
                        propertySource.getName() + "'" + propertySource.getSource().getClass().getName());
                if(!printSource(propertySource)){
                    break;
                }
                System.out.println("====================================================================");
            }
        }
    }

    public static String getApplicationName(){
        String applicationName = environment.resolvePlaceholders("${spring.application.name}");
        if(applicationName == null){
            applicationName = environment.getProperty("spring.application.name");
        }
        return applicationName;
    }

    private static boolean printSource(PropertySource<?> propertySource) {
        if(propertySource.getSource() instanceof Map){
            Map map = (Map)propertySource.getSource();
            Iterator it = map.keySet().iterator();
            while ( it.hasNext()){
                Object key = it.next();
                System.out.println(key +"="+map.get(key));
            }
        }else if(propertySource.getSource() instanceof Properties){
            Properties properties = (Properties)propertySource.getSource();
            for(String key :  properties.stringPropertyNames()){
                System.out.println(key +"="+properties.get(key));
            }
        }else if(propertySource.getSource() instanceof Iterable){
            Iterable iterable = (Iterable)propertySource.getSource();
            Iterator it = iterable.iterator();
            while (it.hasNext()){
                printSource((PropertySource)((ConfigurationPropertySource)it.next()).getUnderlyingSource());
            }
        }else if(propertySource instanceof CompositePropertySource){
            Collection<PropertySource<?>> propertySources = ((CompositePropertySource)propertySource).getPropertySources();
            for(PropertySource<?> compositeProperty : propertySources){
                System.out.println("------------------------------------------");
                System.out.println("---------"+compositeProperty.getName()+"--");
                System.out.println("------------------------------------------");
                printSource(compositeProperty);
            }
            if(onlyprintconfig){
                return false;
            }
        }else{
            System.out.println(((Object) propertySource.getSource()).getClass().getName());
        }
        return true;
    }
}
