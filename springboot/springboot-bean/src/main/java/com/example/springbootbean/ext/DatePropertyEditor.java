package com.example.springbootbean.ext;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 擴展spring獲取bean屬性缺少 date默認轉換實現
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(!StringUtils.isEmpty(text)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.setValue(dateFormat.parse(text));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
