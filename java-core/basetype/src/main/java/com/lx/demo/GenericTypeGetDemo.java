package com.lx.demo;

import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class GenericInterface<T> {
    private T t;

    public void setT(T t) {
        this.t = t;
    }
}

//只有子类填充了泛型, setT时参数为Object, genericTypeGetDemo.setT 看参数
class GenericTypeClass<String> extends GenericInterface {

}

// 子类不写父类写了String, 则表示父类方法中T全部为String
class GenericTypeClass2<E> extends GenericInterface<String> {
    private E e;

    public void setE(E e) {
        this.e = e;
    }
}

// 子类如果增加了泛型占位， 并且创建对象时候填充new xx<String> 则父子类类型相同
class GenericTypeClass3<String> extends GenericInterface<String> {

//    private E e;
//
//    public void setE(E e) {
//        this.e = e;
//    }

}

public class GenericTypeGetDemo {

    private List<Map<String, Object>> value = new ArrayList<>();

    public static void main(String[] args) throws NoSuchFieldException {
//        final GenericTypeClass2 genericTypeClass2 = new GenericTypeClass2();
//        final GenericTypeClass3<String> genericTypeClass3 = new GenericTypeClass3();
        final GenericTypeClass2<String> genericTypeClass2 = new GenericTypeClass2<String>();

        // 获取类的泛型类型
        final GenericTypeClass genericTypeGetDemo = new GenericTypeClass();
        final TypeVariable<? extends Class<? extends GenericTypeClass2>>[] typeParameters =
                GenericTypeClass2.class.getTypeParameters();
        for (TypeVariable<? extends Class<? extends GenericTypeClass2>> typeParameter : typeParameters) {
            System.out.println("泛型类型: " + typeParameter);
        }

        // 获取类的泛型
        ParameterizedType parameterizedType = (ParameterizedType) genericTypeClass2.getClass().getGenericSuperclass();
        final Type type = Arrays.stream(parameterizedType.getActualTypeArguments()).findFirst().orElse(null);
        final String typeName = type.getTypeName();
        System.out.println("实际类型" + typeName);
//        System.out.println(Arrays.toString(parameterizedType.getActualTypeArguments()));

        // 这个么得卵用
        final Type genericSuperclass = genericTypeGetDemo.getClass().getGenericSuperclass();
        System.out.println(genericSuperclass);
        if (genericSuperclass instanceof ParameterizedType) {
            final Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            System.out.println(actualTypeArguments);
        }

        //获取成员泛型类型
        Field field = GenericTypeGetDemo.class.getDeclaredField("value");
        ResolvableType resolvableType = ResolvableType.forField(field);
        System.out.println(resolvableType.getGeneric(0));


        List<? extends Animal> animalsExtends = new ArrayList<>();
//        指定泛型参数为 <? extends Animal> 时，add() 方法的参数变为 ? extends Animal，
//        编译器无法判断这个参数接受的到底是 Animal 的哪种类型，所以它不会接受任何类型
        animalsExtends.add(null); //可以

//        <? extends Animal>表示我是Animal的儿子, 但是你不告诉我具体类型，不让传
//        animalsExtends.add(new Pig()); // 无法编译

//        它表示某种类型的 List，这个类型是 Animal 的基类型。也就是说，我们不知道实际类型是什么，但是这个类型肯定是 Animal 的父类型。
//        因此，我们可以知道向这个 List 添加一个 Animal 或者其子类型的对象是安全的，这些对象都可以向上转型为 Animal

//        <? super Animal> 表示我是animal的爹， 你传啥都行
        List<? super Animal> animalsSuper = new ArrayList<>();
        animalsSuper.add(new Pig()); //正常

    }


    //src 是原始数据的 List，因为要从这里面读取数据，所以用了上边界限定通配符：<? extends T>，取出的元素转型为 T。
    // dest 是要写入的目标 List，所以用了下边界限定通配符：<? super T>，可以写入的元素类型是 T 及其子类型。
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i = 0; i < src.size(); i++)
            dest.set(i, src.get(i));
    }
}

class Animal {

}

class Pig extends Animal {

}
