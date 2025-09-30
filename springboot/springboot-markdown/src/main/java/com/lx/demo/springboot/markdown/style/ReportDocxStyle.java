package com.lx.demo.springboot.markdown.style;

import org.apache.poi.xwpf.usermodel.*;

/**
 * 处理报告文档样式,如果有别的格式可以采用多态
 */
public class ReportDocxStyle {

    // 处理文档样式的方法
    public void processDocumentStyles(XWPFDocument document) {
        // 处理段落样式
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            applyParagraphStyles(paragraph);
        }

        // 处理表格样式
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        applyParagraphStyles(paragraph);
                    }
                }
            }
        }
    }

    // 应用段落样式
    private void applyParagraphStyles(XWPFParagraph paragraph) {
        String paragraphText = paragraph.getText();

        // 识别标题和正文并应用相应样式
        if (isTitle(paragraphText)) {
            applyTitleStyle(paragraph, getTitleLevel(paragraphText));
        } else {
            applyBodyTextStyle(paragraph);
        }
    }

    // 判断是否为标题（根据文本特征简单判断）
    private boolean isTitle(String text) {
        if (text == null || text.trim().isEmpty()) return false;

        String trimmedText = text.trim();
        // 标题通常较短，且可能包含标题特征
        return trimmedText.length() < 50 &&
                (trimmedText.startsWith("第") ||
                        trimmedText.endsWith("章") ||
                        trimmedText.endsWith("节") ||
                        Character.isDigit(trimmedText.charAt(0)));
    }

    // 获取标题级别
    private int getTitleLevel(String titleText) {
        if (titleText.contains("第一章") || titleText.contains("第1章")) return 1;
        if (titleText.contains("第一节") || titleText.contains("第1节")) return 2;
        if (titleText.contains("一、") || titleText.contains("（一）")) return 3;
        if (titleText.contains("1.") && titleText.length() < 20) return 4;
        return 2; // 默认二级标题
    }

    // 应用标题样式
    private void applyTitleStyle(XWPFParagraph paragraph, int level) {
        // 清除现有运行样式
        for (XWPFRun run : paragraph.getRuns()) {
            run.setFontFamily("黑体");
            run.setFontSize(level == 1 ? 20 : (level == 2 ? 18 : 16));
            run.setBold(true);
        }

        // 设置段落间距
        paragraph.setSpacingBefore(level == 1 ? 400 : 200); // 缇为单位
        paragraph.setSpacingAfter(level == 1 ? 300 : 150);
        paragraph.setIndentationLeft(0);
    }

    // 应用正文字体样式
    private void applyBodyTextStyle(XWPFParagraph paragraph) {
        for (XWPFRun run : paragraph.getRuns()) {
            run.setFontFamily("仿宋_GB2312");
            run.setFontSize(14);
            run.setBold(false);
        }

        // 设置正文段落格式
        paragraph.setSpacingBefore(100);
        paragraph.setSpacingAfter(100);
        paragraph.setIndentationFirstLine(567); // 首行缩进2字符（约567缇）
        paragraph.setSpacingBetween(1.5, LineSpacingRule.AUTO);
    }
}
