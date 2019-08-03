package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    /**
     * 上传文件返回服务器绝对路径
     * curl -X POST -F 'file=@/home/lx7ly/Pictures/Wallpapers/666.jpg' http://localhost:9999/upload
     * 文件超过１m测试
     * 异常:
     * {"timestamp":1564807533371,"status":500,"error":"Internal Server Error",
     * "exception":"org.springframework.web.multipart.MultipartException",
     * "message":"Could not parse multipart servlet request; nested exception is
     * java.lang.IllegalStateException:
     *      org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException:
     *      The field file exceeds its maximum permitted size of 1048576 bytes.","path":"/upload"}%
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public String handlerFileUpload(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        final byte[] bytes = file.getBytes();
        final File fileToSave = new File(file.getOriginalFilename());
        FileCopyUtils.copy(bytes, fileToSave);
        return fileToSave.getAbsolutePath();
    }
}
