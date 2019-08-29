package com.lx.demo.springbootenum.web.convert;

import com.lx.demo.springbootenum.contrant.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomStringToEnumConvertFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new CustomStringToEnumConvertFactory.StringToEnum(targetType);
    }

    /**
     * 根据text转换enum
     * @param <T>
     */
    private static class StringToEnum<T extends Enum> implements Converter<String, T> {
        private final Class<T> enumType;

        private Map<String, T> textEnumMap = new HashMap<>();

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
            for (T enumConstant : enumType.getEnumConstants()) {
                // 这里如果为了统一，最好规范一个所有enum的同一校验方法，比如同一加一个getvalue
                //比如增加baseenum,并且有个getbyvalue方法
                textEnumMap.put(((Gender)enumConstant).getText(), enumConstant);
            }
        }

        public T convert(String source) {
            T result = textEnumMap.get(source);
            if(result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}
