package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

import java.util.Collections;

@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(mvcViewFactoryCreator())
                .setDevelopmentMode(true)
                .build();
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ViewResolver viewResolver;

    @Bean
    @DependsOn("viewResolver")
    public MvcViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(
                Collections.singletonList(viewResolver));
        factoryCreator.setUseSpringBeanBinding(true);
        factoryCreator.setApplicationContext(applicationContext);
        return factoryCreator;
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder()
                .setBasePath("/WEB-INF/flow/")
                .addFlowLocationPattern("*-flow.xml")
                .setFlowBuilderServices(flowBuilderServices())
                .build();
    }

    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry()).build();
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        final FlowHandlerMapping handeler = new FlowHandlerMapping();
        handeler.setFlowRegistry(flowRegistry());
        handeler.setFlowUrlHandler(defaultFlowUrlHandler());
//        handeler.setDefaultHandler(new UrlFilenameViewController());
        return handeler;
    }

    @Bean
    public DefaultFlowUrlHandler defaultFlowUrlHandler() {
        return new DefaultFlowUrlHandler();
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter adapter = new FlowHandlerAdapter();
        adapter.setFlowUrlHandler(defaultFlowUrlHandler());
        adapter.setFlowExecutor(flowExecutor());
        return adapter;
    }
}