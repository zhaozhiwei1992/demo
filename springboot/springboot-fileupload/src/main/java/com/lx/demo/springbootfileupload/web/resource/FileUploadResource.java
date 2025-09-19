package com.lx.demo.springbootfileupload.web.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

@RestController
public class FileUploadResource {

    private static final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    @Value("${gateway.base-url:http://your-gateway.com}")
    private String gatewayBaseUrl;

    /**
     * 上传文件
     * @param bucketName: 存储路径
     * @param fileName: 文件名称
     * @param filePath: 文件本地路径，需要加载然后存储到新路径
     * @return
     * @throws Exception
     */
    @GetMapping("/uploadAndGetUrl")
    public String uploadAndGetUrl() throws Exception {
        String bucketName = "images", fileName="jk-flow.jpg", filePath="/tmp/11.jpg";
        // 方式1: 使用minio
        // MinioClient minioClient = MinioClient.builder()
        // .endpoint("http://10.10.115.10:9002")
        // .credentials("minioadmin", "minioadmin")
        // .build();
//        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//        }
//        minioClient.uploadObject(UploadObjectArgs.builder()
//                .bucket(bucketName)
//                .object(fileName)
//                .filename(filePath)
//                .build());
//        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
//                .bucket(bucketName)
//                .object(fileName)
//                .method(Method.GET)
//                .build());

        // 方式2: 直接存储到服务器
        // 现场要求，上传到服务器目录，方便省厅读取
        // 1. 根据bucket创建一个目录, 跟jar同级即可
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            url = FileUploadResource.class.getResource("/");
        }
        String path = url.getPath().replaceAll("//", "/").replaceAll("file:", "");
        File bucketDir = new File(path + File.separator + bucketName);
        if (!bucketDir.exists() && !bucketDir.mkdirs()) {
            throw new IOException("Failed to create bucket directory: " + bucketDir.getAbsolutePath());
        }
        //上传目录: /jar所在绝对路径/springboot-fileupload-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/images
        log.info("上传目录: {}", bucketDir.getAbsolutePath());
        // 2. 将文件全部放到这个目录下
        File destFile = new File(bucketDir, fileName);
        try (InputStream in = new FileInputStream(filePath);
             OutputStream out = new FileOutputStream(destFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        // 3. 返回服务器地址信息 一体化网关地址 + 系统标识 + bucket + 文件名
        // 格式示例: http://your-gateway.com/your-system-id/images/jk-flow.jpg
        return String.format("%s/%s/%s/%s",
                gatewayBaseUrl.trim(),
                "esms",
                bucketName,
                fileName);
    }

    @GetMapping(value = "/images", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(String fileName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            url = FileUploadResource.class.getResource("/");
        }
        String path = url.getPath().replaceAll("//", "/").replaceAll("file:", "");
        File bucketDir = new File(path + File.separator + "images");
        if (!bucketDir.exists() && !bucketDir.mkdirs()) {
            throw new IOException("Failed to create bucket directory: " + bucketDir.getAbsolutePath());
        }
        //上传目录: /jar所在绝对路径/springboot-fileupload-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/images
        File destFile = new File(bucketDir, fileName);
        // 记录目录信息，便于调试
        log.info("图片目录: {}", bucketDir.getAbsolutePath());

        // 检查文件是否存在
        if (!destFile.exists()) {
            throw new IOException("File not found: " + destFile.getAbsolutePath());
        }

        // 读取文件内容并返回字节数组
        return Files.readAllBytes(destFile.toPath());
    }

    private byte[] db = null;

    @GetMapping("/upload/db")
    public String uploadAndGetUrl2DB() throws Exception {
        String bucketName = "images", fileName="jk-flow.jpg", filePath="/tmp/11.jpg";

        File sourceFile = new File(filePath);
        db = Files.readAllBytes(sourceFile.toPath());
        System.out.println("File content: " + new String(db));
        // 3. 返回服务器地址信息 一体化网关地址 + 系统标识 + bucket + 文件名
        // 格式示例: http://your-gateway.com/your-system-id/images/jk-flow.jpg
        return String.format("%s/%s/%s/%s",
                gatewayBaseUrl.trim(),
                "esms",
                bucketName,
                fileName);
    }

    /*
    * http://localhost:8080/images/db?fileName=jk-flow.jpg
     */
    @GetMapping(value = "/images/db", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageFromDB(String fileName) throws IOException {
        if (db == null) {
            throw new IOException("File not found: " + fileName);
        }
        return db;
    }
}
