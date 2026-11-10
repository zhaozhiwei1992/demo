package com.lx.demo.springbootfileupload.web.controller;

import com.lx.demo.springbootfileupload.util.zip.ZipCompressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class FileDownloadController {

  private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

  private static final String UPLOADED_FOLDER = "/tmp/";

  @Autowired private RestTemplate restTemplate;

  /**
   * 跳转到download页面
   *
   * @return
   */
  @GetMapping("/to-download")
  public String toDownload() {
    return "download";
  }

  /**
   * 1. 前端使用: window.open下载
   *
   * <p>后端必须正确设置 ~Content-Disposition~ header，否则浏览器拿不到文件名会显示 undefined。
   *
   * <p>- ~filename=~ 后面必须加引号，否则特殊字符会导致整个 header 失效 - 中文文件名必须用 ~filename*=UTF-8''~ 编码 -
   * 如果接口有登录拦截/重定向，~window.open~ 会直接翻车 - Nginx/网关可能覆盖或吞掉 ~Content-Disposition~
   */
  @GetMapping("/download")
  public void download(@RequestParam("agentCode")String agentCode, HttpServletResponse response) throws IOException {

    // 1. 鉴权校验
    // if (!validateToken(aitoken)) {
    //    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    //    return;
    // }

    // 2. 准备文件（这里用临时 zip 举例）
    File zipFile = File.createTempFile("download-", ".zip");
    ZipCompressUtils.zipDirectory(new File("/tmp/logs"), zipFile);

    // 3. 设置响应头 —— 方式1文件名必须在这里给, 如果是中文必须encode
    String fileName = agentCode + ".zip";
//    String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

    response.setContentType("application/zip");
    response.setHeader(
        "Content-Disposition",
        "attachment; filename=\"download.zip\"; filename*=UTF-8''" + fileName);
    response.setContentLengthLong(zipFile.length());

    // 4. 写流
    try (InputStream in = new FileInputStream(zipFile);
         OutputStream out = response.getOutputStream()) {
      byte[] buffer = new byte[8192];
      int len;
      while ((len = in.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      out.flush();
    }

    zipFile.delete();
  }
}
