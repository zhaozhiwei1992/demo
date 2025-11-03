package com.lx.demo.springboot.markdown;

import com.lx.demo.springboot.markdown.util.HtmlTableParser;
import org.apache.commons.io.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HTMLToDocx {

    public static void main(String[] args) {
        try {
            // 1. 你的原始HTML内容
            String htmlContent = "<h1><center><font style=\"font-weight: bold;font-size: 25px;\">2024年1-9月市本级国库集中支付预算执行情况</font></center></h1>\n" +
                    "<h2>一、总体情况</h2>\n" +
                    "<p>&emsp;&emsp;1-9月，市本级国库集中支付资金15867234.56元<font color=\"red\">（单位支出明细表支付数据金额合计）</font>、比上年同期（下同）上升17.32%</p>";
            // 读取前端生成的html文本
            InputStream resourceAsStream = HTMLToDocx.class.getClassLoader().getResourceAsStream("mock/markdown.txt.html");
            // 获取html文本, 并写到htmlContent中
            htmlContent = IOUtils.toString(resourceAsStream);

            // 2. 生成标准格式docx
            byte[] bytes = new HTMLToDocx().convertHtmlToDocx(htmlContent, "");
            File outputFile = new File("/tmp/output.docx");
            // 将bytes写入文件
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile)) {
                fos.write(bytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] convertHtmlToDocx(String htmlContent, String tokenid) throws Exception {
        // 加载doc模板，处理标题 参考https://deepmind.t-salon.cc/article/6292
        XWPFDocument model = new XWPFDocument(new FileInputStream("/home/zhaozhiwei/Downloads/export_template.docx"));
        CTStyles modelStyle = model.getStyle();
        System.out.println(modelStyle);

        // 使用XWPFDocument创建标准文档
        XWPFDocument document = new XWPFDocument();

        XWPFStyles docStyle = document.createStyles();
        // 设置新建文档样式为模板中读取到的样式
        docStyle.setStyles(modelStyle);

        // 解析HTML并转换为DOCX段落
        org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(htmlContent);

        for (org.jsoup.nodes.Element element : jsoupDoc.body().children()) {
            if (element.tagName().equalsIgnoreCase("h1") ||
                    element.tagName().equalsIgnoreCase("h2") ||
                    element.tagName().equalsIgnoreCase("h3") ||
                    element.tagName().equalsIgnoreCase("h4")) {
                // 处理标题
                createHeading(document, element.text(), getHeadingLevel(element.tagName()));
            } else if (element.tagName().equalsIgnoreCase("p")) {
                // 处理段落
//                createParagraph(document, element.text());
                createParagraphWithFormatting(document, element);
            } else if (element.tagName().equals("img")) {
                createImg(document, element.attr("src"), tokenid);
            } else if (element.tagName().equalsIgnoreCase("table")) {
//                createTable(document, element);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.write(baos);
        document.close();
        baos.flush();

        return baos.toByteArray();
    }

    /**
     * 创建带格式的段落
     */
    private void createParagraphWithFormatting(XWPFDocument document, org.jsoup.nodes.Element element) {
        XWPFParagraph paragraph = document.createParagraph();
        // 设置段落样式
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.setSpacingBetween(1, LineSpacingRule.AUTO);

        // 递归处理元素的所有子节点，保留格式
        processChildNodes(paragraph, element);
    }

    /**
     * 递归处理子节点，保留格式信息
     */
    private void processChildNodes(XWPFParagraph paragraph, org.jsoup.nodes.Node parentNode) {
        for (org.jsoup.nodes.Node node : parentNode.childNodes()) {
            if (node instanceof org.jsoup.nodes.TextNode) {
                // 文本节点，直接添加文本
                org.jsoup.nodes.TextNode textNode = (org.jsoup.nodes.TextNode) node;
                if (!textNode.text().trim().isEmpty()) {
                    XWPFRun run = paragraph.createRun();
                    run.setText(textNode.text());
                }
            } else if (node instanceof org.jsoup.nodes.Element) {
                // 元素节点，处理格式
                org.jsoup.nodes.Element childElement = (org.jsoup.nodes.Element) node;
                processFormattedElement(paragraph, childElement);
            }
        }
    }

    /**
     * 处理带格式的元素
     */
    private void processFormattedElement(XWPFParagraph paragraph, org.jsoup.nodes.Element element) {
        String tagName = element.tagName().toLowerCase();

        // 单独处理 <br> 标签
        if ("br".equals(tagName)) {
            // 创建新的Run并添加换行符
            XWPFRun run = paragraph.createRun();
            run.addBreak(); // 使用Apache POI的换行方法
            return; // <br> 标签没有子元素和文本内容，直接返回
        }

        String text = element.text();
        if (text.trim().isEmpty()) {
            return;
        }

        XWPFRun run = paragraph.createRun();
        run.setText(text);

        // 根据标签类型设置格式
        switch (tagName) {
            case "strong":
            case "b":
                run.setBold(true);
                break;
            case "em":
            case "i":
                run.setItalic(true);
                break;
            case "u":
                run.setUnderline(UnderlinePatterns.SINGLE);
                break;
            case "span":
                // 可以处理span的样式
                String style = element.attr("style");
                if (style.contains("font-weight:bold")) {
                    run.setBold(true);
                }
                break;
        }

        // 递归处理嵌套的格式标签
        if (!element.children().isEmpty()) {
            for (org.jsoup.nodes.Element child : element.children()) {
                processFormattedElement(paragraph, child);
            }
        }
    }

    /*
     * 按照现场模板格式，重新生成表格
     */
    private void createTable(XWPFDocument document, Element element) {
        HtmlTableParser.TableData tableData = HtmlTableParser.parseHtmlTable(element.html());
        // 计算表格总行数：表头行数 + 数据行数
        int totalHeaderRows = tableData.getHeaders().size();
        int totalDataRows = tableData.getRows().size();
        // 确定列数：取第一行表头的单元格数（假设所有行列数一致）
        int numOfColumns = tableData.getHeaders().get(0).size();

        // 创建指定行数和列数的表格
        XWPFTable table = document.createTable(totalHeaderRows + totalDataRows, numOfColumns);
        table.setTableAlignment(TableRowAlign.CENTER); // 表格整体居中
        table.setWidth(400);

        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
//        CTTblBorders borders = tblPr.addNewTblBorders();
//        setBorder(borders.addNewTop(), STBorder.SINGLE);     // 上边框
//        setBorder(borders.addNewBottom(), STBorder.SINGLE);  // 下边框
//        setBorder(borders.addNewLeft(), STBorder.SINGLE);    // 左边框
//        setBorder(borders.addNewRight(), STBorder.SINGLE);   // 右边框
//        setBorder(borders.addNewInsideH(), STBorder.SINGLE); // 内部水平边框
//        setBorder(borders.addNewInsideV(), STBorder.SINGLE); // 内部垂直边框

        // 设置表格宽度为100%页面宽度
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setType(STTblWidth.PCT); // 百分比类型
        tblWidth.setW(BigInteger.valueOf(100 * 50)); // 100%宽度 (100 * 50 = 5000, 即100%)

        // 设置表格在页面居中
//        CTJc jc = tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc();
//        jc.setVal(STJc.CENTER);

        // 设置自动调整布局以适应内容
        CTTblLayoutType layoutType = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
        layoutType.setType(STTblLayoutType.AUTOFIT);

        // 1. 填充表头
        for (int i = 0; i < totalHeaderRows; i++) {
            List<String> headerRowData = tableData.getHeaders().get(i);
            XWPFTableRow headerRow = table.getRow(i);
            for (int j = 0; j < numOfColumns; j++) {
                XWPFTableCell cell = headerRow.getCell(j);
                // 清除单元格默认的空白段落
                cell.removeParagraph(0);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                // 创建新段落并设置文本
                XWPFParagraph paragraph = cell.addParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(headerRowData.get(j));
                run.setFontFamily("黑体"); // 设置字体
                run.setFontSize(11); // 设置字体大小
            }
        }

        // 2. 填充数据行
        for (int i = 0; i < totalDataRows; i++) {
            List<String> rowData = tableData.getRows().get(i);
            // 注意表格行索引：从 table 创建的第 totalHeaderRows 行开始是数据行
            XWPFTableRow dataRow = table.getRow(i + totalHeaderRows);
            for (int j = 0; j < numOfColumns; j++) {
                XWPFTableCell cell = dataRow.getCell(j);
                cell.removeParagraph(0);
                XWPFParagraph paragraph = cell.addParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(rowData.get(j));
                // 可以为数据行设置不同的样式
                run.setFontFamily("仿宋_GB2312"); // 设置字体
                run.setFontSize(10); // 设置字体大小
                //  如果是汉字居左，纯数字居中, 暂时全部居中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
        }
    }

    /*
     * 自动下载图片，并填充到docx中
     */
    private void createImg(XWPFDocument document, String src, String tokenid) {
        src += ("&tokenid=" + tokenid);
        InputStream imageStream = null;
        try {
            // 创建URL对象并建立连接
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 10秒连接超时
            connection.setReadTimeout(30000);    // 30秒读取超时

            // 获取输入流
            imageStream = connection.getInputStream();

            // 创建段落和运行对象
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            // 根据图片URL判断图片类型
            int pictureType = XWPFDocument.PICTURE_TYPE_PNG; // 默认PNG

            // 将图片添加到文档中，设置宽度为400像素（可根据需要调整）
            run.addPicture(imageStream, pictureType, "image", Units.toEMU(400), Units.toEMU(300));

        } catch (Exception e) {
            System.err.println("图片处理失败 [" + src + "]: " + e.getMessage());
        } finally {
            // 确保输入流被关闭
            if (imageStream != null) {
                try {
                    imageStream.close();
                } catch (Exception e) {
                    System.err.println("关闭流时发生错误: " + e.getMessage());
                }
            }
        }
    }

    /*
     * 创建标题，以提供模板为准
     */
    private void createHeading(XWPFDocument document, String text, int level) {
        if (level == 1) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle(level + 1 + "");
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            paragraph.setSpacingBetween(1, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(100);
            paragraph.setSpacingAfter(100);
//            paragraph.setIndentationLeft(100);
//            paragraph.setIndentationRight(100);

            XWPFRun run = paragraph.createRun();
            run.setText(text);
//            run.setFontSize(22);  // 一级标题
//            run.setFontFamily("方正小标宋简体");
        } else if (level == 2) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle(level + 1 + "");
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setSpacingBetween(1, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(100);
            paragraph.setSpacingAfter(100);

            XWPFRun run = paragraph.createRun();
            run.setText(text);
//            run.setFontSize(16);  // 二级标题
//            run.setFontFamily("黑体");
        } else if (level == 3) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle(level + 1 + "");
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setSpacingBetween(1, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(100);
            paragraph.setSpacingAfter(100);

            XWPFRun run = paragraph.createRun();
            run.setText(text);
//            run.setFontSize(16);  // 三级标题
//            run.setFontFamily("方正楷体简体");
//            run.setBold(true);
        } else if (level == 4) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle(level + 1 + "");
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setSpacingBetween(1, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(100);
            paragraph.setSpacingAfter(100);

            XWPFRun run = paragraph.createRun();
            run.setText(text);
//            run.setFontSize(12);  // 4级标题
//            run.setFontFamily("方正黑体_GBK");
//            run.setBold(true);
        }
    }

    private void createParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.setSpacingBetween(1, LineSpacingRule.AUTO);

        XWPFRun run = paragraph.createRun();
        run.setText(text);
//        run.setFontSize(16);
//        run.setFontFamily("仿宋_GB2312");

        // 设置正文样式
        paragraph.setSpacingBefore(100);
        paragraph.setSpacingAfter(100);
//        paragraph.setIndentationFirstLine(600); // 首行缩进
    }

    private int getHeadingLevel(String tagName) {
        switch (tagName.toLowerCase()) {
            case "h1":
                return 1;
            case "h2":
                return 2;
            case "h3":
                return 3;
            case "h4":
                return 4;
            case "h5":
                return 5;
            default:
                return 0;
        }
    }

}