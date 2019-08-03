package com.lx.demo;

import ch.qos.logback.classic.db.SQLBuilder;
import org.odata4j.expression.*;
import org.odata4j.producer.resources.OptionsQueryParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class OdataMain {
    public static void main(String[] args) {
        String search="((Name eq 'Milk' and Price lt '2.55') or (Name eq 'zhangsan')) and (age lt 19 and age gt 3)";

//        CommonExpression actual = ExpressionParser.parse(search);
//        System.out.println(actual);
        //这里可以作为校验的方式
        final BoolCommonExpression boolCommonExpression = OptionsQueryParser.parseFilter(search);
        System.out.println(boolCommonExpression);
//        CommonExpression actual = ExpressionParser.parse(search);
//        LiteralExpression literal = (LiteralExpression) actual;
//        Object value = Expression.literalValue(literal);
//        System.out.println(value);

        CommonExpression expr = ExpressionParser.parse(search);
        final String s = Expression.asPrintString(expr);
        //or(boolParen(and(eq(simpleProperty(Name),string(Milk)),lt(simpleProperty(Price),string(2.55)))),boolParen(eq(simpleProperty(Name),string(zhangsan))))
        final String s1 = Expression.asFilterString(expr);
        System.out.println(s);
        System.out.println(s1);
//        new FilterExpressionVisitor().visit(search);
//        ODataSQLBuilder visitor = new ODataSQLBuilder(Mockito.mock(MetadataStore.class), false);
//        visitor.visitNode(expr);
//        SpringApplication.run(OdataMain.class);
    }
}
