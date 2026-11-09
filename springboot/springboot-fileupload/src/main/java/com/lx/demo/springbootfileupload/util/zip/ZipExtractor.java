package com.lx.demo.springbootfileupload.util.zip;

import java.io.IOException;
import java.nio.file.Path;

/**
 * zip 解压器。
 *
 * <p>两种实现，业务规则（保留父目录 / 旧目录备份 / 原子切换 / 防 zip slip / 防 zip bomb）
 * 全部在 {@link AbstractZipExtractor}，实现类只负责「怎么读 zip」：
 * <ul>
 *     <li>{@link CommonsCompressZipExtractor}：commons-compress，支持 Zip64、更多压缩方法，生产推荐</li>
 *     <li>{@link JdkZipExtractor}：JDK 自带 java.util.zip，零依赖，只支持 stored/deflate</li>
 * </ul>
 */
public interface ZipExtractor {

    /**
     * 解压 zip 到目标目录。
     *
     * @param zipFile      本地 zip 文件（可能是临时文件名）
     * @param targetDir    目标目录，不存在会自动创建
     * @param rootNameHint 压缩包内没有公共父目录时用作父目录名的提示（传原始上传文件名）
     * @return 解压结果
     * @throws IOException 压缩包损坏、编码无法识别、路径非法、超出大小限制
     */
    Result extract(Path zipFile, Path targetDir, String rootNameHint) throws IOException;

    /**
     * 解压结果。
     */
    final class Result {

        private final Path rootDir;
        private final int fileCount;
        private final long totalBytes;
        private final Path backupDir;

        Result(Path rootDir, int fileCount, long totalBytes, Path backupDir) {
            this.rootDir = rootDir;
            this.fileCount = fileCount;
            this.totalBytes = totalBytes;
            this.backupDir = backupDir;
        }

        /**
         * 解压后的根目录，例如 /tmp/demo
         */
        public Path getRootDir() {
            return rootDir;
        }

        public int getFileCount() {
            return fileCount;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        /**
         * 被替换下来的旧目录备份路径，首次上传（没有旧目录）时为 null
         */
        public Path getBackupDir() {
            return backupDir;
        }
    }
}
