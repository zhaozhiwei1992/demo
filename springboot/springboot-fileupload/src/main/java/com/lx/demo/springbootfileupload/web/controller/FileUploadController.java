package com.lx.demo.springbootfileupload.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    @GetMapping("/")
    public String index(){
        return "upload";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    private static final String UPLOADED_FOLDER = "/tmp/";
    /**
     * 上传文件返回服务器绝对路径
     * curl -X POST -F 'file=@/home/lx7ly/Pictures/Wallpapers/666.jpg' http://localhost:8080/upload
     * 文件超过１m测试
     * 异常:
     * {"timestamp":1564807533371,"status":500,"error":"Internal Server Error",
     * "exception":"org.springframework.web.multipart.MultipartException",
     * "message":"Could not parse multipart servlet request; nested exception is
     * java.lang.IllegalStateException:
     *      org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException:
     *      The field file exceeds its maximum permitted size of 1048576 bytes.","path":"/upload"}%
     *
     * 参考springboot官方文档
     * For example, if you want to specify
     * that files be unlimited, set the spring.servlet.multipart.max-file-size property to -1.
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public String handlerFileUpload(
            @RequestParam(value = "file", required = true) MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException {
//        final byte[] bytes = file.getBytes();
//        final File fileToSave = new File(file.getOriginalFilename());
//        FileCopyUtils.copy(bytes, fileToSave);
//        return fileToSave.getAbsolutePath();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadMuti")
    public String uploadMuti(){
        return "uploadMuti";
    }

    @PostMapping("/batch/upload")
    public String handlerFileUploadMulti(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
        final List<MultipartFile> files = ((MultipartHttpServletRequest) httpServletRequest).getFiles("file");

        final ArrayList<String> filePathArrayList = new ArrayList<>();

        files.stream().filter(multipartFile -> !multipartFile.isEmpty()).forEach(multipartFile -> {

            try {
                byte[] bytes = multipartFile.getBytes();
//                final File file = new File(multipartFile.getOriginalFilename());
//                FileCopyUtils.copy(bytes, file);
//                filePathArrayList.add(file.getAbsolutePath());
                final BufferedOutputStream outputStream =
                        new BufferedOutputStream(new FileOutputStream(multipartFile.getOriginalFilename()));
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + filePathArrayList + "'");
        return "redirect:/uploadStatus";
    }
}
