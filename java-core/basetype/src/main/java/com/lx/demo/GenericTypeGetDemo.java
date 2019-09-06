package com.lx.demo;

import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class GenericInterface<T>{

}

class GenericTypeClass<String> extends GenericInterface{

}

public class GenericTypeGetDemo{

    private List<Map<String, Object>> value = new ArrayList<>();

    public static void main(String[] args) throws NoSuchFieldException {
        // 获取类的泛型类型
        final GenericTypeClass genericTypeGetDemo = new GenericTypeClass();
        final TypeVariable<? extends Class<? extends GenericTypeClass>>[] typeParameters =
                genericTypeGetDemo.getClass().getTypeParameters();
        for (TypeVariable<? extends Class<? extends GenericTypeClass>> typeParameter : typeParameters) {
            System.out.println("泛型类型: " + typeParameter);
        }

        // 这个么得卵用
        final Type genericSuperclass = genericTypeGetDemo.getClass().getGenericSuperclass();
        System.out.println(genericSuperclass);
        if(genericSuperclass instanceof ParameterizedType){
            final Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            System.out.println(actualTypeArguments);
        }

        //获取成员泛型类型
        Field field = GenericTypeGetDemo.class.getDeclaredField("value");
        ResolvableType resolvableType = ResolvableType.forField(field);
        System.out.println(resolvableType.getGeneric(0));


    }
}
