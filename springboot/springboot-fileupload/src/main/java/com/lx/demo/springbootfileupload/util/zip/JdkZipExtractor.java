package com.lx.demo.springbootfileupload.util.zip;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/**
 * 零依赖实现：JDK 自带 java.util.zip。
 *
 * <p>限制：只支持 stored / deflate 两种压缩方法，Zip64 大包支持不完整，
 * 文件名编码只能整体猜（UTF-8 失败回退 GBK）。生产建议用 {@link CommonsCompressZipExtractor}。
 */
public class JdkZipExtractor extends AbstractZipExtractor {

    @Override
    protected Scan scan(Path zipFile) throws IOException {
        Charset[] candidates = {StandardCharsets.UTF_8, Charset.forName("GBK")};
        Throwable lastError = null;
        for (Charset charset : candidates) {
            try {
                return new Scan(charset, readNames(zipFile, charset));
            } catch (IllegalArgumentException ex) {
                lastError = ex;
            } catch (ZipException ex) {
                // JDK 把「文件名解码失败」包装成了 ZipException，其他损坏场景直接抛出
                if (!isBadEntryName(ex)) {
                    throw ex;
                }
                lastError = ex;
            }
        }
        throw new IOException("无法识别压缩包内文件名编码: " + zipFile.getFileName(), lastError);
    }

    @Override
    protected void readEntries(Path zipFile, Charset charset, EntryConsumer consumer) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(Files.newInputStream(zipFile)), charset)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                consumer.accept(entry.getName(), entry.isDirectory(), zis);
                zis.closeEntry();
            }
        }
    }

    private static List<String> readNames(Path zipFile, Charset charset) throws IOException {
        List<String> names = new ArrayList<>();
        try (InputStream in = new BufferedInputStream(Files.newInputStream(zipFile));
             ZipInputStream zis = new ZipInputStream(in, charset)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                names.add(entry.getName());
                zis.closeEntry();
            }
        }
        return names;
    }

    private static boolean isBadEntryName(ZipException ex) {
        String message = ex.getMessage();
        return message != null && message.contains("bad entry name");
    }
}
