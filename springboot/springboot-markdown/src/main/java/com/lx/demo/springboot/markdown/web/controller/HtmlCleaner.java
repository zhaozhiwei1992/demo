package com.lx.demo.springboot.markdown.web.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;

public class HtmlCleaner {
    
    public static String cleanHtml(String html) {
        // 方法1: 使用 XML 解析模式
        Document doc = Jsoup.parse(html, "", Parser.xmlParser());
        doc.outputSettings()
            .syntax(Document.OutputSettings.Syntax.xml)
            .escapeMode(Entities.EscapeMode.xhtml)
            .prettyPrint(false); // 禁用美化输出，保持原始结构
        
        return doc.body().html();
    }
    
    // 方法2: 更严格的清理，确保标签正确闭合
    public static String strictCleanHtml(String html) {
        try {
            // 先尝试用 HTML 解析器，它会自动修复一些标签问题
            Document doc = Jsoup.parse(html);
            doc.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml)
                .escapeMode(Entities.EscapeMode.xhtml)
                .prettyPrint(false);
            
            String cleaned = doc.body().html();
            
            // 验证标签是否平衡
            if (!isTagsBalanced(cleaned)) {
                // 如果标签不平衡，使用更严格的方法
                return forceBalanceTags(html);
            }
            
            return cleaned;
        } catch (Exception e) {
            // 如果解析失败，尝试手动修复
            return forceBalanceTags(html);
        }
    }
    
    // 简单的标签平衡检查
    private static boolean isTagsBalanced(String html) {
        int centerOpen = countOccurrences(html, "<center");
        int centerClose = countOccurrences(html, "</center>");
        return centerOpen == centerClose;
    }
    
    private static int countOccurrences(String text, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }
    
    // 强制平衡标签（简单实现）
    private static String forceBalanceTags(String html) {
        // 这里可以添加更复杂的标签平衡逻辑
        // 暂时先确保 center 标签平衡
        StringBuilder result = new StringBuilder(html);
        
        int centerOpen = countOccurrences(html, "<center");
        int centerClose = countOccurrences(html, "</center>");
        
        // 如果开始标签多于结束标签，添加缺失的结束标签
        while (centerOpen > centerClose) {
            result.append("</center>");
            centerClose++;
        }
        
        return result.toString();
    }
}