package com.example.springbootdruid.config;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author qiu
 */
public class LoadXmlBeanDefinitionReader extends XmlBeanDefinitionReader {

    /**
     * Create new XmlBeanDefinitionReader for the given bean factory.
     *
     * @param registry the BeanFactory to load bean definitions into,
     *                 in the form of a BeanDefinitionRegistry
     */
    public LoadXmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        //super(registry);
        super(((DefaultListableBeanFactory) registry).getParentBeanFactory()!=null?((BeanDefinitionRegistry)((DefaultListableBeanFactory) registry).getParentBeanFactory()):registry);
    }

    public boolean isWebapp(){
        boolean webapp = false;
        return webapp;
    }

    private boolean isSuportJTA(){
        return false;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        //如果无业务模块，则加载数据源及事务设置
        super.loadBeanDefinitions(location);

        //读取各个模块下的module.properties配置文件
        String[] configLocations = new String[]{"bdg-context.xml"};
        if (configLocations != null && configLocations.length>0) {
            for (String configLocation : configLocations) {
                super.loadBeanDefinitions(configLocation);
            }
        }
        return configLocations.length;
    }
}
