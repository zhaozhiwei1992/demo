package com.lx.demo.springbooteasyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.lx.demo.springbooteasyexcel.domain.DemoData;
import com.lx.demo.springbooteasyexcel.listener.DemoDataListener;
import com.lx.demo.springbooteasyexcel.listener.MutiSheetHeaderReadListener;
import com.lx.demo.springbooteasyexcel.listener.MutiSheetReadListener;
import com.lx.demo.springbooteasyexcel.listener.SingleSheetReadListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.*;

@SpringBootTest
class ReadTest {

    @Test
    void contextLoads() {
    }

    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() throws FileNotFoundException {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
        fileName = "demo.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    @Test
    public void dynamicReadSheet0() {
        String fileName = "/d/at_work/一体化系统文档/安徽/预算执行报表/财政部规范表样展现/2023总决算报表参数_20240612-标准2.0_bak.xls";
        SingleSheetReadListener listener = new SingleSheetReadListener();
        EasyExcel.read(fileName, listener)
                .headRowNumber(1) // 指定第一行为头行
                .sheet(0)
                .doRead();

        final List<Map<String, String>> datas = listener.getDatas();
        System.out.println(datas);
        // 处理listener中的数据
        // 例如，保存到数据库
    }

    @Test
    public void dynamicReadAll() {
        String fileName = "/d/at_work/一体化系统文档/安徽/预算执行报表/财政部规范表样展现/2023总决算报表参数_20240612-标准2.0_bak.xls";
        MutiSheetReadListener listener = new MutiSheetReadListener();
        EasyExcel.read(fileName, listener)
                .headRowNumber(1) // 指定第一行为头行
                .doReadAll();

        final Map<String, List<Map<String, String>>> datas = listener.getSheetDatas();
        System.out.println();
        // 处理listener中的数据
        // 例如，保存到数据库
    }

    @Test
    public void headerBuild() {
        String fileName = "/d/at_work/一体化系统文档/安徽/预算执行报表/财政部规范表样展现/2023年总决算录入表-20231229正式EXCEL_bak.xls";
        MutiSheetHeaderReadListener listener = new MutiSheetHeaderReadListener();
        EasyExcel.read(fileName, listener)
//				.extraRead(CellExtraTypeEnum.MERGE)
                .doReadAll();

        final Map<String, List<Map<Integer, String>>> datas = listener.getSheetDatas();
//		System.out.println(datas);
        List<List<String>> headers = new ArrayList<>();
        final List<Map<Integer, String>> l06 = datas.get("L06");
        for (int i = 0; i < l06.size(); i++) {
            if (i > 1 && i < 5) {
                final Map<Integer, String> map = l06.get(i);
                final List<String> list = new ArrayList<>();
                list.addAll(map.values());
                headers.add(list);
            }
        }
        System.out.println(headers);
        final String s = buildTableHeader(headers);
        System.out.println(s);
        // 处理listener中的数据
        // 例如，保存到数据库
    }

    /**
     * @param data :
     * @data: 2024/7/12-上午9:46
     * @User: zhaozhiwei
     * @method: buildTableHeader
     * @return: java.lang.String
     * @Description: [
     * [科目编码, 科目名称, 预算数, 变动项目, null, null, null, 调整预算数],
     * [null, null, null, 上级专项调整数, null, null, 增加(减少)预算指标, null],
     * [null, null, null, 小计, 企业上下划, 其他, null, null]
     * ]
     */
    private static String buildTableHeader(List<List<String>> data) {
        // 1. 数据纵向合并补充, 将null部分按照上一行相同位置值补充
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == null) {
                    if (i > 0 && j < data.get(i - 1).size()) {
                        row.set(j, data.get(i - 1).get(j));
                    }
                }
            }
        }
        // 2. 数据横向合并补充, 将null部分按前边第一个不为空的填充
        data.forEach(row -> {
            String rowValue = null;
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i) == null) {
                    row.set(i, rowValue);
                } else {
                    rowValue = row.get(i);
                }
            }
        });
        StringBuilder headerBuilder = new StringBuilder();
        int maxDepth = data.stream().mapToInt(List::size).max().orElse(0);

        for (int i = 0; i < maxDepth; i++) {
            // 如果全部相同也要分割，用list, set会去重
            Set<String> currentLevel = new HashSet<>();
//            List<String> currentLevel = new ArrayList<>();
            for (List<String> level : data) {
                if (i < level.size() && level.get(i) != null) {
                    currentLevel.add(level.get(i));
                }
            }

            if (!currentLevel.isEmpty()) {
                // 构建当前层级的表头字段
                String levelHeader = String.join("_", currentLevel);
                headerBuilder.append(levelHeader).append("\t");
            }
        }

        return headerBuilder.toString().trim();
    }
}
