package com.lx.demo.springbootfileupload.web.controller;

import com.lx.demo.springbootfileupload.domain.FileEntity;
import com.lx.demo.springbootfileupload.web.dto.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 跳转到upload plugin页面
     * @return
     */
    @GetMapping("/to_upload_plugin")
    public String toUploadPlugin(){
        return "upload_plugin";
    }

    /**
     * 获取已经上传文件，填充前台列表
     *
     obj.id=file_id ;
     obj.name=n.filebasename;
     obj.filename=n.filename;
     obj.getStatus=function() {
     return '';
     };
     obj.statusText='';
     obj.size=n.filelen;
     * @param request
     * @return
     */
    @PostMapping("/fileList")
    @ResponseBody
    public Map<String, Object> getFileList(HttpServletRequest request){

        final ArrayList<FileEntity> fileEntities = new ArrayList<>();

        final FileEntity fileEntity = new FileEntity();
        fileEntity.setObjectId("1");
        fileEntity.setFileName("test");
        fileEntity.setFileSize("1000");
        fileEntity.setExtend(".jpg");
        fileEntities.add(fileEntity);

        fileEntity.setObjectId("2");
        fileEntity.setFileName("test2");
        fileEntity.setFileSize("1000");
        fileEntity.setExtend(".jpg");
        fileEntities.add(fileEntity);

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("rows", fileEntities);

        return hashMap;
    }

    /**
     * 上传文件 针对文件上传
     *
     * @return
     * ,consumes = {"multipart/form-data","text/html","application/json","application/x-shockwave-flash"},produces={"application/json"}
     */
    @PostMapping(value = "autoSaveFile")
    public void autoSaveFile(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
            mulRequest.setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = mulRequest.getFileMap();
            // 上传的文件名
            String fileName = "";
            // 上传文件名的后缀
            String extend = "";
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                // 获取上传文件对象
                MultipartFile mulFile = entry.getValue();
                // 获取文件名
                fileName = mulFile.getOriginalFilename();
                // 获取文件扩展名
                //计算文件大小

                logger.info("文件名 {}", fileName);

                // step2.上传文件至服务器
                // lsy update
                String filePath = "";

                // 本地存储
                j.setObj(1);
                j.setSuccess(true);
                j.setMessage("上传成功！");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter pw = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            pw = response.getWriter();
            pw.write(com.alibaba.fastjson.JSON.toJSONString(j));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pw.flush();
            pw.close();
        }
    }

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
