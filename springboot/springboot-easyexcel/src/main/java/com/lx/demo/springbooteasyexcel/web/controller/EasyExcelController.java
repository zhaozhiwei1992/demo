package com.lx.demo.springbooteasyexcel.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.lx.demo.springbooteasyexcel.domain.DemoData;
import com.lx.demo.springbooteasyexcel.listener.DemoDataListener;
import com.lx.demo.springbooteasyexcel.repository.DemoDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试ali excel工具
 */
@RestController
public class EasyExcelController {

    private static List<List<String>> head() {
        List<List<String>> headTitles = new ArrayList();
        String basicInfo = "基础资料", skuInfo = "商品扩展", orderInfo = "经营情况", empty = " ";
        //第一列，1/2/3行
        headTitles.add(Arrays.asList(basicInfo, basicInfo, "类别"));
        //第二列，1/2/3行
        headTitles.add(Arrays.asList(basicInfo, basicInfo, "名称"));
        List<String> skuTitles = Arrays.asList("组合商品", "上一次优惠时间", "销售次数", "库存", "价格");
        skuTitles.forEach(title -> {
            headTitles.add(Arrays.asList(skuInfo, skuInfo, title));
        });
        List<Integer> monthList = Arrays.asList(5, 6);
        //动态根据月份生成
        List<String> orderSpeaces = Arrays.asList("销售额", "客流", "利润");
        monthList.forEach(month -> {
            orderSpeaces.forEach(title -> {
                headTitles.add(Arrays.asList(orderInfo, month + "月", title));
            });
        });
        //无一、二行标题
        List<String> lastList = Arrays.asList("日均销售金额(元)", "月均销售金额(元)");
        lastList.forEach(title -> {
            headTitles.add(Arrays.asList(empty, empty, title));
        });
        System.out.println(headTitles);
        return headTitles;
    }

    private static List<List<Object>> contentData() {
        List<List<Object>> contentList = new ArrayList<>();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        contentList.add(Arrays.asList("测试", "商品A", "苹果🍎"));
        contentList.add(Arrays.asList("测试", "商品B", "橙子🍊"));
        return contentList;
    }

    /**
     * 通过动态字段方式导出数据
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/dynamic/download")
    public void dynamicDownload(HttpServletResponse response) throws IOException {

        try {
            // 文件输出位置
//            OutputStream out = new FileOutputStream("/tmp/test.xlsx");
            String fileName = "easyexcel动态导出测试.xlsx";
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("filename", URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            final ServletOutputStream outputStream = response.getOutputStream();
            ExcelWriter writer = EasyExcelFactory.write(outputStream).build();

            // 动态添加表头，适用一些表头动态变化的场景
            WriteSheet sheet1 = new WriteSheet();
            sheet1.setSheetName("商品明细");
            sheet1.setSheetNo(0);
            // 创建一个表格，用于 Sheet 中使用
            WriteTable table = new WriteTable();
            table.setTableNo(1);
            table.setHead(head());

            WriteSheet sheet2 = new WriteSheet();
            sheet2.setSheetName("商品明细2");
//            编号要注意，一定是顺次增加的
            sheet2.setSheetNo(1);

            // 写数据, 这里参数都要动态
            writer.write(contentData(), sheet1, table);
            writer.write(contentData(), sheet2, table);
            writer.finish();
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象方式操作 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 设置返回的 参数
     * <p>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("模板").doWrite(data());
    }

    /**
     * 对象方式 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener(new DemoDAO())).sheet().doRead();
        return "success";
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
