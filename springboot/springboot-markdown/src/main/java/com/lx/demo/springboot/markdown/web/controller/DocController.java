package com.lx.demo.springboot.markdown.web.controller;

import com.lx.demo.springboot.markdown.style.ReportDocxStyle;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class DocController {

    /**
     * 跳转到upload plugin页面
     *
     * @return
     */
    @GetMapping("/to_upload")
    public String toUploadPlugin() {
        return "upload";
    }

    @ResponseBody
    @PostMapping("/upload")
    public void handlerFileUpload(
            @RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletResponse response) throws IOException {

        // 1. 获取docx文件
        // 2. 分别解析docx文件，设置正文字体间距, 将标题全部转换为normal并根据不同的标题级次设置不同字体以及间距
        // 3. 返回下载链接，下载文件
        try (InputStream inputStream = file.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // 调试信息：检查文档结构
            System.out.println("文档段落数量: " + document.getParagraphs().size());
            System.out.println("文档表格数量: " + document.getTables().size());

            // 检查底层XML结构
            if (document.getDocument() != null && document.getDocument().getBody() != null) {
                System.out.println("文档主体存在");
                System.out.println("段落数量(XML): " + document.getDocument().getBody().getPArray().length);
                System.out.println("表格数量(XML): " + document.getDocument().getBody().getTblArray().length);
            }


            // 处理文档样式
            new ReportDocxStyle().processDocumentStyles(document);

            // 将处理后的文档写入输出流
            document.write(baos);
            byte[] processedDocBytes = baos.toByteArray();

            // 设置响应头
            String downloadFilename = "template.docx"; // 指定下载的文件名
            response.setContentType("application/octet-stream"); // 设置为二进制流，通用类型
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFilename + "\"");
            response.setContentLength(processedDocBytes.length);

            // 写入响应流
            try (OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(processedDocBytes);
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("文件处理失败: " + e.getMessage());
        }

    }

}
