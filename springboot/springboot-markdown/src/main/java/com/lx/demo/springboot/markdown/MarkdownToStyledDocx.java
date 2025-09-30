package com.lx.demo.springboot.markdown;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import java.io.File;

public class MarkdownToStyledDocx {

    public static void main(String[] args) {
        try {
            // 1. 你的原始HTML内容
            String htmlContent = "<h1><center><font style=\"font-weight: bold;font-size: 25px;\">2024年1-9月市本级国库集中支付预算执行情况</font></center></h1>\n" +
                                "<h2>一、总体情况</h2>\n" +
                                "<p>&emsp;&emsp;1-9月，市本级国库集中支付资金15867234.56元<font color=\"red\">（单位支出明细表支付数据金额合计）</font>、比上年同期（下同）上升17.32%</p>";

            // 2. 使用JSoup清理和规范化HTML，输出为XHTML格式（这对docx4j很重要）
            Document jsoupDoc = Jsoup.parse(htmlContent);
            jsoupDoc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            jsoupDoc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
            String xhtmlContent = jsoupDoc.body().html(); // 获取body内的HTML

            // 3. 加载你已经定义好所有样式的Word模板
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("/tmp/template.docx"));
            
            // 4. 创建XHTML导入器并导入内容
            // XHTMLImporter会尝试将HTML标签映射到Word模板中同名的样式
            XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
            // 将转换后的内容添加到文档的主体中
            wordMLPackage.getMainDocumentPart().getContent().addAll(XHTMLImporter.convert(xhtmlContent, null));

            // 5. 保存最终生成的文档
            wordMLPackage.save(new File("/tmp/output_with_template_styles.docx"));

            System.out.println("文档已成功生成并应用模板样式！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}