package com.example.springbootechart.web.resource;

import com.example.springbootechart.web.dto.ExternalOption;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用echart java包直接获取json数据，前台展示
 */
@RestController
public class EchartsController {

    private static final Logger logger = LoggerFactory.getLogger(EchartsController.class);
    /**
     * option = {
     *     title: {
     *         text: '惠民惠农财政补贴项目',
     *         subtext: '按项目大类统计资金分配情况'
     *     },
     *     legend: {
     *         show: true,
     *         data: ['金额范围', '均值']
     *     },
     *     grid: {
     *         top: 100
     *     },
     *     angleAxis: {
     *         type: 'category',
     *         data: cities
     *     },
     *     tooltip: {
     *         show: true,
     *         formatter: function (params) {
     *             var id = params.dataIndex;
     *             return cities[id] + '<br>最低：' + data[id][0] + '<br>最高：' + data[id][1] + '<br>平均：' + data[id][2];
     *         }
     *     },
     *     radiusAxis: {
     *     },
     *     polar: {
     *     },
     *     series: [{
     *         type: 'bar',
     *         itemStyle: {
     *             color: 'transparent'
     *         },
     *         data: data.map(function (d) {
     *             return d[0];
     *         }),
     *         coordinateSystem: 'polar',
     *         stack: '最大最小值',
     *         silent: true
     *     }, {
     *         type: 'bar',
     *         data: data.map(function (d) {
     *             return d[1] - d[0];
     *         }),
     *         coordinateSystem: 'polar',
     *         name: '金额范围',
     *         stack: '最大最小值'
     *     }, {
     *         type: 'bar',
     *         itemStyle: {
     *             color: 'transparent'
     *         },
     *         data: data.map(function (d) {
     *             return d[2] - barHeight;
     *         }),
     *         coordinateSystem: 'polar',
     *         stack: '均值',
     *         silent: true,
     *         z: 10
     *     }, {
     *         type: 'bar',
     *         data: data.map(function (d) {
     *             return barHeight * 2;
     *         }),
     *         coordinateSystem: 'polar',
     *         name: '均值',
     *         stack: '均值',
     *         barGap: '-100%',
     *         z: 10
     *     }]
     * };
     */
    @GetMapping(value = "/project1")
    public Option test1(){

        String[] cities = {"扶贫", "农业生产发展", "就业补贴", "医疗补助", "退耕还林还草", "林业和草原", "水利"};

        ExternalOption option = new ExternalOption();

        // 大标题、小标题、位置
        option.title().text("惠民惠农财政补贴项目").subtext("按项目大类统计资金分配情况").x("left");
        option.legend().show(true).data("金额范围", "均值");
        option.grid().setTop(100);
        option.angleAxis().type("category").data(cities);

        //显示工具提示,设置提示格式
        option.tooltip().show(true).formatter("{a} <br/>{b} : {c}");

        Bar[] bars = new Bar[cities.length];
        // 循环数据
        for (int i = 0; i < cities.length; i++) {
            // 图类别(柱状图)
            Bar bar = new Bar();
            // 类目对应的柱状图
            Map<String, Object> map = new HashMap<>(2);
//            map.put("value", 1);
//            map.put("itemStyle", new ItemStyle().normal(new Normal().color("transparent")));
            bar.data("test");
            bar.itemStyle(new ItemStyle().normal(new Normal().color("transparent")));
            bar.coordinateSystem("polar");
            bar.name("均值");
            bar.stack("均值");
            bar.stack("最大最小值");
            bar.z(10);
            bars[i] = bar;
        }

        option.series(bars);
        logger.info(option.toPrettyString());
        return option;
    }
    /**
     * 柱状图
     *
     * @param isHorizontal
     *            是否水平放置
     */
    @GetMapping(value = "/testBar/{isHorizontal}")
    public void testBar(@PathVariable boolean isHorizontal) {
        String[] citis = { "广州", "深圳", "珠海", "汕头", "韶关", "佛山" };
        int[] datas = { 6030, 7800, 5200, 3444, 2666, 5708 };
        String[] colors = { "rgb(2,111,230)", "rgb(186,73,46)", "rgb(78,154,97)", "rgb(2,111,230)", "rgb(186,73,46)", "rgb(78,154,97)" };
        String title = "地市数据";

        GsonOption option = new GsonOption();

        option.title(title); // 标题
        // 工具栏
        option.toolbox().show(true).feature(Tool.mark, // 辅助线
                Tool.dataView, // 数据视图
                new MagicType(Magic.line, Magic.bar),// 线图、柱状图切换
                Tool.restore,// 还原
                Tool.saveAsImage);// 保存为图片

        option.tooltip().show(true).formatter("{a} <br/>{b} : {c}");//显示工具提示,设置提示格式

        option.legend(title);// 图例

        Bar bar = new Bar(title);// 图类别(柱状图)
        CategoryAxis category = new CategoryAxis();// 轴分类
        category.data(citis);// 轴数据类别
        // 循环数据
        for (int i = 0; i < citis.length; i++) {
            int data = datas[i];
            String color = colors[i];
            // 类目对应的柱状图
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("value", data);
            map.put("itemStyle", new ItemStyle().normal(new Normal().color(color)));
            bar.data(map);
        }

        if (isHorizontal) {// 横轴为类别、纵轴为值
            option.xAxis(category);// x轴
            option.yAxis(new ValueAxis());// y轴
        } else {// 横轴为值、纵轴为类别
            option.xAxis(new ValueAxis());// x轴
            option.yAxis(category);// y轴
        }

        option.series(bar);
    }
}
