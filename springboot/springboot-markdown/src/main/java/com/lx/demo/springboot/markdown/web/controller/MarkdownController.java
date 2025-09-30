package com.lx.demo.springboot.markdown.web.controller;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class MarkdownController {

    /**
     * 跳转到upload plugin页面
     * @return
     */
    @GetMapping("/to_markdown_preview")
    public String toUploadPlugin(){
        return "markdown_preview";
    }

    // 处理导出请求
    @PostMapping("/export/docx")
    public void exportToDocx(@RequestParam("content") String markdownContent,
                             HttpServletResponse response) throws IOException, Docx4JException {
        // 1. 将markdown转换为html
        markdownContent = markdownContent.replaceAll("\\\\n", "\n").replaceAll("undefined", "");
        String markdownToHtml = parseMarkdownToHtml(markdownContent);

        // 2. 优化 HTML 格式
        System.out.println("cleanedHtml 前:");
        System.out.println(markdownToHtml);
//        String cleanedHtml = HtmlCleaner.strictCleanHtml(markdownToHtml);
//        System.out.println("cleanedHtml 后:");
//        System.out.println(cleanedHtml);

        // 3. html转docx
        // 创建 WordprocessingMLPackage 实例
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        // 使用 XHTMLImporterImpl 将 HTML 导入到 Word 文档中
        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordPackage);
        mainDocumentPart.getContent().addAll(xhtmlImporter.convert(markdownToHtml, null));


        // 将输入流直接导出(测试)
        String fileName = "template.docx"; // 指定下载的文件名
        response.setContentType("application/octet-stream"); // 设置为二进制流，通用类型
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        // 如果知道文件大小，可以设置 Content-Length
        // response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));

        // 3. 获取响应输出流并传输数据
        try (OutputStream outputStream = response.getOutputStream()) {
            // 将 Word 文档写入 ByteArrayOutputStream
            wordPackage.save(outputStream);
        } catch (IOException e) {
            // 处理IO异常
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public String parseMarkdownToHtml(String markdown) {
        // 创建配置集
        MutableDataSet options = new MutableDataSet();
        options.set(HtmlRenderer.ESCAPE_HTML, false);
        options.set(HtmlRenderer.ESCAPE_HTML_BLOCKS, false);

        // 创建解析器和渲染器
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // 解析 Markdown 文本
        Node document = parser.parse(markdown);

        // 渲染为 HTML
        return renderer.render(document).replaceAll("&lt;font", "<font");
    }

    /*
      * 导出到模板
     */
    @PostMapping("/export/docx/with/style")
    public void exportToDocxWithStyle(@RequestParam("content") String markdownContent,
                             HttpServletResponse response) throws IOException {
        // 1. 将markdown转换为html
        markdownContent = markdownContent.replaceAll("\\\\n", "\n").replaceAll("undefined", "");
        String markdownToHtml = parseMarkdownToHtml(markdownContent);

        // 2. 优化 HTML 格式

        // 3. html转docx

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("docs/template.docx");

        // 将输入流直接导出(测试)
//        String fileName = "template.docx"; // 指定下载的文件名
//        response.setContentType("application/octet-stream"); // 设置为二进制流，通用类型
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        // 如果知道文件大小，可以设置 Content-Length
        // response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));

        // 3. 获取响应输出流并传输数据
//        try (OutputStream outputStream = response.getOutputStream(); // 使用 try-with-resources 确保流关闭
//             InputStream inputStream = resourceAsStream) {
//            byte[] buffer = new byte[10240]; // 10KB 缓冲区 [6](@ref)
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//            outputStream.flush(); // 确保所有数据都已写出
//        } catch (IOException e) {
//            // 处理IO异常
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
    }

}
