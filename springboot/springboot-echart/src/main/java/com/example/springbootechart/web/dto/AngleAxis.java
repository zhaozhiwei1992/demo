package com.example.springbootechart.web.dto;

import com.github.abel533.echarts.Basic;
import com.github.abel533.echarts.Component;
import com.github.abel533.echarts.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 极坐标图扩展
 */
public class AngleAxis extends Basic<AngleAxis> implements Data<AngleAxis>, Component {
    private String category;

    private List data;

    public List data() {
        if (this.data == null) {
            this.data = new ArrayList();
        }

        return this.data;
    }

    @Override
    public AngleAxis data(Object... values) {
        if (values != null && values.length != 0) {
            this.data().addAll(Arrays.asList(values));
            return this;
        } else {
            return this;
        }
    }

    public AngleAxis type(String category) {
        this.category = category;
        return this;
    }
}
