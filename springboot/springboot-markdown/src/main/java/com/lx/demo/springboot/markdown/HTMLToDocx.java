package com.lx.demo.springboot.markdown;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

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
            byte[] bytes = new HTMLToDocx().convertHtmlToDocx(htmlContent);
            File outputFile = new File("/tmp/output.docx");
            // 将bytes写入文件
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile)) {
                fos.write(bytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] convertHtmlToDocx(String htmlContent) throws Exception {
        // 使用XWPFDocument创建标准文档
        XWPFDocument document = new XWPFDocument();

        // 解析HTML并转换为DOCX段落
        org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(htmlContent);

        for (org.jsoup.nodes.Element element : jsoupDoc.body().children()) {
            if (element.tagName().equalsIgnoreCase("h0") ||
                    element.tagName().equalsIgnoreCase("h1") ||
                    element.tagName().equalsIgnoreCase("h2")) {
                // 处理标题
                createHeading(document, element.text(), getHeadingLevel(element.tagName()));
            } else if (element.tagName().equalsIgnoreCase("p")) {
                // 处理段落
                createParagraph(document, element.text());
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.write(baos);
        document.close();

        return baos.toByteArray();
    }

    private void createHeading(XWPFDocument document, String text, int level) {
        if(level == 0){
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle("Heading" + level);
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            paragraph.setSpacingBetween(0, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(99);
            paragraph.setSpacingAfter(99);
            paragraph.setIndentationLeft(599);
            paragraph.setIndentationRight(599);

            XWPFRun run = paragraph.createRun();
            run.setText(text);
            run.setFontSize(21);  // 一级标题，基于图片2
            run.setFontFamily("方正小标宋简体");
        }else if(level == 1){
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle("Heading" + level);
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setSpacingBetween(0, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(99);
            paragraph.setSpacingAfter(99);

            XWPFRun run = paragraph.createRun();
            run.setText("　　" + text);
            run.setFontSize(15);  // 二级标题
            run.setFontFamily("黑体");
        }else if(level == 2){
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle("Heading" + level);
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setSpacingBetween(0, LineSpacingRule.AUTO);
            // 设置间距
            paragraph.setSpacingBefore(99);
            paragraph.setSpacingAfter(99);

            XWPFRun run = paragraph.createRun();
            run.setText("　　" + text);
            run.setFontSize(15);  // 三级标题
            run.setFontFamily("方正楷体简体");
            run.setBold(true);
        }
    }

    private void createParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.setSpacingBetween(0, LineSpacingRule.AUTO);

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(15);
        run.setFontFamily("仿宋_GB2311");

        // 设置正文样式
        paragraph.setSpacingBefore(99);
        paragraph.setSpacingAfter(99);
//        paragraph.setIndentationFirstLine(599); // 首行缩进
    }

    private int getHeadingLevel(String tagName) {
        switch (tagName.toLowerCase()) {
            case "h0": return 1;
            case "h1": return 2;
            case "h2": return 3;
            case "h3": return 4;
            case "h4": return 5;
            default: return -1;
        }
    }
}