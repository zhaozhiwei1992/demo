package com.lx.demo.springbootfileupload.web.controller;

import com.lx.demo.springbootfileupload.domain.FileEntity;
import com.lx.demo.springbootfileupload.util.zip.ZipExtractor;
import com.lx.demo.springbootfileupload.web.dto.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * zip 解压实现，由 file.upload.zip-extractor 选择 commons-compress（默认）或 jdk
     */
    @Autowired
    private ZipExtractor zipExtractor;

    /**
     * zip 解压的默认目标目录，调用时可用 targetDir 参数覆盖
     */
    @Value("${file.upload.zip-target-dir:/tmp}")
    private String zipTargetDir;

    /**
     * curl -X POST http://127.0.0.1:8080/send -F 'url="http://127.0.0.1:8080/upload"' -F 'path=/tmp/test/1.txt'
     * 指定文件转发到上传服务里
     * 查看上传目录下存在1.txt即可
     * @param url
     * @param path
     * @return
     */
    @PostMapping("/send")
    public String send(String url,String path) {

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        // 设置请求的格式类型
        headers.setContentType(type);
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        ResponseEntity<String> responseResponseEntity = restTemplate.postForEntity(url, files, String.class);
        return responseResponseEntity.getBody();
    }

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

    @GetMapping("/upload")
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

    /**
     * 上传 zip 压缩包，按压缩包内原有目录结构解压到本地目录（默认 /tmp）。
     *
     * <p>解压后会固定落在一个父目录下，不会把一堆文件直接倒进目标目录：
     * <ul>
     *     <li>压缩包内所有条目同一个一级目录（常见：demo/a.txt、demo/b/c.txt）-&gt; 保留 demo</li>
     *     <li>压缩包内文件直接躺在根下 -&gt; 用压缩包文件名当父目录</li>
     *     <li>目标目录已有同名父目录 -&gt; 旧目录改名成「目录名-bak-时间」备份，新内容仍落在原目录名上
     *         （以新的为准，备份默认只留最近 5 份）</li>
     * </ul>
     *
     * <p>curl 测试：
     * <pre>
     * curl -F 'file=@/tmp/demo.zip' http://127.0.0.1:8080/upload/zip
     * curl -F 'file=@/tmp/demo.zip' -F 'targetDir=/tmp/unzip' http://127.0.0.1:8080/upload/zip
     * </pre>
     * 注意：targetDir 由客户端指定只适合内网/调试，生产环境应固定目录或做白名单校验。
     *
     * @param file      上传的 zip 文件
     * @param targetDir 解压目标目录，为空取配置 file.upload.zip-target-dir，默认 /tmp
     * @return 解压结果：实际根目录、文件数、总字节
     */
    @ResponseBody
    @PostMapping("/upload/zip")
    public AjaxJson handlerZipUpload(@RequestParam(value = "file", required = true) MultipartFile file,
                                     @RequestParam(value = "targetDir", required = false) String targetDir) {
        AjaxJson result = new AjaxJson();
        String originalFilename = file.getOriginalFilename();
        if (file.isEmpty() || originalFilename == null
                || !originalFilename.toLowerCase().endsWith(".zip")) {
            return result.setAjResult("请上传 .zip 压缩包", "9998", false, null);
        }

        String dir = targetDir == null || targetDir.trim().isEmpty() ? zipTargetDir : targetDir.trim();
        Path zipTmp = null;
        try {
            // 先落临时文件，避免把整个 zip 读进内存（MultipartFile.getBytes 大包会 OOM）
            zipTmp = Files.createTempFile("upload-", ".zip");
            file.transferTo(zipTmp.toFile());

            ZipExtractor.Result extractResult = zipExtractor.extract(zipTmp, Paths.get(dir), originalFilename);
            logger.info("zip 解压完成 dir={} root={} backup={} files={} bytes={}", dir,
                    extractResult.getRootDir(), extractResult.getBackupDir(),
                    extractResult.getFileCount(), extractResult.getTotalBytes());

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("targetDir", dir);
            data.put("rootDir", extractResult.getRootDir().toString());
            data.put("backupDir", extractResult.getBackupDir() == null
                    ? "" : extractResult.getBackupDir().toString());
            data.put("extractor", zipExtractor.getClass().getSimpleName());
            data.put("fileCount", extractResult.getFileCount());
            data.put("totalBytes", extractResult.getTotalBytes());
            return result.setAjResult("解压成功", "9999", true, data);
        } catch (IOException | RuntimeException e) {
            logger.error("zip 解压失败 dir={} file={}", dir, originalFilename, e);
            return result.setAjResult("解压失败: " + e.getMessage(), "9997", false, null);
        } finally {
            if (zipTmp != null) {
                try {
                    Files.deleteIfExists(zipTmp);
                } catch (IOException e) {
                    logger.warn("临时文件删除失败: {}", zipTmp, e);
                }
            }
        }
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
