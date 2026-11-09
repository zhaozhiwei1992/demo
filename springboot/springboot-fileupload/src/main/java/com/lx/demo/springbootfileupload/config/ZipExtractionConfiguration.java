package com.lx.demo.springbootfileupload.config;

import com.lx.demo.springbootfileupload.util.zip.CommonsCompressZipExtractor;
import com.lx.demo.springbootfileupload.util.zip.JdkZipExtractor;
import com.lx.demo.springbootfileupload.util.zip.ZipExtractor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zip 解压实现选择。
 *
 * <pre>
 * file.upload.zip-extractor=commons-compress   # 默认，支持 Zip64 / 更多压缩方法
 * file.upload.zip-extractor=jdk                # 零依赖，只支持 stored/deflate
 * </pre>
 *
 * 两种实现共用 AbstractZipExtractor 里的业务规则（父目录保留、备份、原子切换、zip slip 校验）。
 */
@Configuration
public class ZipExtractionConfiguration {

    @Bean
    @ConditionalOnProperty(name = "file.upload.zip-extractor", havingValue = "jdk")
    public ZipExtractor jdkZipExtractor() {
        return new JdkZipExtractor();
    }

    @Bean
    @ConditionalOnProperty(name = "file.upload.zip-extractor", havingValue = "commons-compress",
            matchIfMissing = true)
    public ZipExtractor commonsCompressZipExtractor() {
        return new CommonsCompressZipExtractor();
    }
}
