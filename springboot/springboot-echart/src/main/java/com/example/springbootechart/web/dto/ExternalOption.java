package com.example.springbootechart.web.dto;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.json.OptionUtil;

/**
 * 扩展基础option
 */
public class ExternalOption extends Option {

    private AngleAxis angleAxis;

    public AngleAxis angleAxis() {
        if (this.angleAxis == null) {
            this.angleAxis = new AngleAxis();
        }

        return this.angleAxis;
    }

    public ExternalOption() {
    }

    public void view() {
        OptionUtil.browse(this);
    }

    public String toString() {
        return GsonUtil.format(this);
    }

    public String toPrettyString() {
        return GsonUtil.prettyFormat(this);
    }

    public String exportToHtml(String fileName) {
        return this.exportToHtml(System.getProperty("java.io.tmpdir"), fileName);
    }

    public String exportToHtml(String filePath, String fileName) {
        return OptionUtil.exportToHtml(this, filePath, fileName);
    }
}
