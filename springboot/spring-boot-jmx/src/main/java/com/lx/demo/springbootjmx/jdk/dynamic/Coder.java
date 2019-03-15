package com.lx.demo.springbootjmx.jdk.dynamic;

import javax.management.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.management.MBeanOperationInfo.ACTION;

/**
 * jdk dynamicmbean
 * @link https://github.com/mercyblitz/segmentfault-lessons/blob/master/spring-boot/lesson-17/spring-boot-lesson-17/src/main/java/com/segmentfault/springbootlesson17/mbean/dynamic/SimpleDynamicMBean.java
 */
public class Coder implements DynamicMBean {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    /**
     * 获取属性值， 通过反射得方式可以不用老改代码
     * @param attribute
     * @return
     * @throws AttributeNotFoundException
     * @throws MBeanException
     * @throws ReflectionException
     */
    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
//        return value;
        try {
            Field declaredField = this.getClass().getDeclaredField(attribute);
            declaredField.setAccessible(true);
            return declaredField.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
//        if("value".equalsIgnoreCase(attribute.getName())){
//            this.value = String.valueOf(attribute.getValue());
//        }
        try {
            Field declaredField = this.getClass().getDeclaredField(attribute.getName());
            declaredField.setAccessible(true);
            declaredField.set(this, attribute.getValue());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param attributes 所有的属性名
     * @return
     */
    @Override
    public AttributeList getAttributes(String[] attributes) {
        Arrays.stream(attributes).forEach(System.out::println);
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        System.out.println("setattrabutes" + attributes);
        return null;
    }

    /**
     *
     * @param actionName 操作方法名
     * @param params
     * @param signature
     * @return
     * @throws MBeanException
     * @throws ReflectionException
     */
    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
//        if("displayValue".equalsIgnoreCase(actionName)){
//            return value;
//        }
//        return null;
        try {
            Method declaredMethod = this.getClass().getDeclaredMethod(actionName, null);
            return declaredMethod.invoke(this, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个方法必须得lu对了
     * 这里会初始化你这个mbean定义的属性，以及获取的方式
     * @return
     */
    @Override
    public MBeanInfo getMBeanInfo() {

        Field[] declaredFields = this.getClass().getDeclaredFields();
        List<MBeanAttributeInfo> mBeanAttributeInfos = new ArrayList<>();
        MBeanConstructorInfo mBeanConstructorInfo = new MBeanConstructorInfo(this.getClass().getSimpleName(), "默认构造器", new MBeanParameterInfo[0]);
        List<MBeanOperationInfo> mBeanOperationInfos = new ArrayList<MBeanOperationInfo>();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            String typeName = declaredField.getType().getName();
            mBeanAttributeInfos.add(new MBeanAttributeInfo(name, typeName, "动态属性" + name, true, true, false));
            String methodName = getMethodName(name, true, false);
            mBeanOperationInfos.add(new MBeanOperationInfo(methodName, "自定义 " + methodName + " 方法", new MBeanParameterInfo[0], typeName, ACTION));
        }


//        MBeanInfo mBeanInfo = new MBeanInfo(
//                this.getClass().getName(),
//                "简单的自定义DynamicMBean",
//                of(new MBeanAttributeInfo("value", String.class.getName(), "动态属性value", true, true, false)),
//                of(new MBeanConstructorInfo(this.getClass().getSimpleName(), "默认构造器", new MBeanParameterInfo[0])),
//                of(new MBeanOperationInfo("getValue", "自定义 displayValue 方法", new MBeanParameterInfo[0], String.class.getName(), ACTION)),
//                new MBeanNotificationInfo[0]);
        MBeanInfo mBeanInfo = new MBeanInfo(
                this.getClass().getName(),
                "简单的自定义DynamicMBean",
                mBeanAttributeInfos.toArray(new MBeanAttributeInfo[declaredFields.length]),
                of(new MBeanConstructorInfo(this.getClass().getSimpleName(), "默认构造器", new MBeanParameterInfo[0])),
                mBeanOperationInfos.toArray(new MBeanOperationInfo[declaredFields.length]),
                new MBeanNotificationInfo[0]);
        return mBeanInfo;
    }

    /**
     * 多个属性转数组
     * @param array
     * @param <T>
     * @return
     */
    private static <T> T[] of(T... array) {
        return array;
    }

    /**
     * 获取方法名
     * @param name
     * @param getter
     * @param is
     * @return
     */
    private String getMethodName(String name, boolean getter, boolean is) {
        StringBuilder sb = new StringBuilder();
        if (getter) {
            if (is) {
                sb.append("is");
            } else {
                sb.append("get");
            }
        } else {
            sb.append("set");
        }

        sb.append(Character.toUpperCase(name.charAt(0)));
        sb.append(name.substring(1));
        return sb.toString();
    }
}
