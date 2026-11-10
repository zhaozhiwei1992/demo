package com.lx.demo.springbootfileupload.util.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.Deflater;

public class ZipCompressUtils {

    /**
     * 压缩目录
     *
     * @param sourceDir 要压缩的目录
     * @param zipFile   输出的 zip 文件
     */
    public static void zipDirectory(File sourceDir, File zipFile) throws IOException {
        try (ZipArchiveOutputStream zos =
                     new ZipArchiveOutputStream(new FileOutputStream(zipFile))) {

            zos.setLevel(Deflater.DEFLATED);

            Files.walkFileTree(sourceDir.toPath(), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String entryName = sourceDir.toPath()
                            .relativize(file)
                            .toString()
                            .replace("\\", "/");

                    ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
                    zos.putArchiveEntry(entry);

                    try (InputStream is = Files.newInputStream(file)) {
                        IOUtils.copy(is, zos);
                    }

                    zos.closeArchiveEntry();
                    return FileVisitResult.CONTINUE;
                }
            });

            zos.finish();
        }
    }
}