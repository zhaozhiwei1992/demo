///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.example.springbootbean.event;
//
//import io.microsphere.util.StopWatch;
//import org.springframework.beans.PropertyValues;
//import org.springframework.beans.factory.BeanNameAware;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//import java.util.Objects;
//import java.util.StringJoiner;
//
///**
// * Bean Time Statistics
// *
// * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
// * @since 1.0.0
// */
//public class BeanTimeStatistics implements BeanListener, BeanNameAware {
//
//    private final StopWatch stopWatch = new StopWatch("spring.context.beans");
//
//    private String beanName;
//
//    @Override
//    public boolean supports(String beanName) {
//        return !isIgnoredBean(beanName);
//    }
//
//    @Override
//    public void onBeanDefinitionReady(String beanName, RootBeanDefinition mergedBeanDefinition) {
//        if (isIgnoredBean(beanName)) {
//            return;
//        }
//        stopWatch.start("ready." + beanName);
//    }
//
//    @Override
//    public void onBeforeBeanInstantiate(String beanName, RootBeanDefinition mergedBeanDefinition) {
//        stopWatch.start("instantiation." + beanName);
//    }
//
//    @Override
//    public void onBeforeBeanInstantiate(String beanName, RootBeanDefinition mergedBeanDefinition, Constructor<?> constructor, Object[] args) {
//        stopWatch.start("instantiation." + beanName);
//    }
//
//    @Override
//    public void onBeforeBeanInstantiate(String beanName, RootBeanDefinition mergedBeanDefinition, Object factoryBean, Method factoryMethod, Object[] args) {
//        stopWatch.start("instantiation." + beanName);
//    }
//
//    @Override
//    public void onAfterBeanInstantiated(String beanName, RootBeanDefinition mergedBeanDefinition, Object bean) {
//        stopWatch.stop();
//    }
//
//    @Override
//    public void onBeanPropertyValuesReady(String beanName, Object bean, PropertyValues pvs) {
//    }
//
//    @Override
//    public void onBeforeBeanInitialize(String beanName, Object bean) {
//        stopWatch.start("initialization." + beanName);
//    }
//
//    @Override
//    public void onAfterBeanInitialized(String beanName, Object bean) {
//        stopWatch.stop();
//    }
//
//    @Override
//    public void onBeanReady(String beanName, Object bean) {
//        if (isIgnoredBean(beanName)) {
//            return;
//        }
//        stopWatch.stop();
//    }
//
//    @Override
//    public void onBeforeBeanDestroy(String beanName, Object bean) {
//        stopWatch.start("destroy." + beanName);
//    }
//
//    @Override
//    public void onAfterBeanDestroy(String beanName, Object bean) {
//        stopWatch.stop();
//    }
//
//    public StopWatch getStopWatch() {
//        return stopWatch;
//    }
//
//    @Override
//    public String toString() {
//        return new StringJoiner(", ", BeanTimeStatistics.class.getSimpleName() + "[", "]")
//                .add(stopWatch.toString())
//                .toString();
//    }
//
//    private boolean isIgnoredBean(String beanName) {
//        return Objects.equals(this.beanName, beanName);
//    }
//
//    @Override
//    public void setBeanName(String name) {
//        this.beanName = name;
//    }
//}