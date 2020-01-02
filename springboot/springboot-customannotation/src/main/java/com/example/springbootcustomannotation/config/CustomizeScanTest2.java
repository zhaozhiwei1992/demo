package com.example.springbootcustomannotation.config;

import com.example.springbootcustomannotation.service.MyComponent2Bean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 自定义扫描
 */
@Configuration
public class CustomizeScanTest2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(CustomizeScanTest2.class);
        annotationConfigApplicationContext.refresh();
        MyComponent2Bean injectClass = annotationConfigApplicationContext.getBean(MyComponent2Bean.class);
        injectClass.echo("hello world");
    }

    /**
     * 这里一定要注意, 这个BeanScannerConfigurer初始化一定要被扫描到才可以使用
     * BeanScannerConfigurer用于嵌入到Spring的加载过程的中，这里用到了BeanFactoryPostProcessor 和 ApplicationContextAware。
     * Spring提供了一些的接口使程序可以嵌入Spring的加载过程。这个类中的继承ApplicationContextAware接口，
     * Spring会读取ApplicationContextAware类型的的JavaBean
     * ，并调用setApplicationContext(ApplicationContext applicationContext)传入Spring的applicationContext。
     * 同样继承BeanFactoryPostProcessor接口，Spring会在BeanFactory的相关处理完成后调用postProcessBeanFactory方法，进行定制的功能。
     *
     */
    @Component
    public static class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {
        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext = applicationContext;
        }
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
          Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
          scanner.setResourceLoader(this.applicationContext);
          scanner.scan("com.example.springbootcustomannotation");
        }
      }
}