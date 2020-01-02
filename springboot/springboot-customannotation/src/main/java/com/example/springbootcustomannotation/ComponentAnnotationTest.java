package com.example.springbootcustomannotation;

import com.example.springbootcustomannotation.annotation.CustomizeCompenent1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 这里configuration是必须的
 */
@Configuration
public class ComponentAnnotationTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(ComponentAnnotationTest.class);
        annotationConfigApplicationContext.refresh();
        InjectClass injectClass = annotationConfigApplicationContext.getBean(InjectClass.class);
        injectClass.print();
    }

    @CustomizeCompenent1
    public static class InjectClass {
        public void print() {
            System.out.println("hello world");
        }
    }
}