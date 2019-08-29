package com.lx.demo.springbootenum.web.convert;

import com.lx.demo.springbootenum.contrant.Gender;
import org.springframework.core.convert.converter.Converter;

public class StringToGenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String s) {
        return Gender.getByStatus(Integer.parseInt(s));
    }
}
