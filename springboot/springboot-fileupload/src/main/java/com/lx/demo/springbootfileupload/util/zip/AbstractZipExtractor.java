package com.lx.demo.springbootfileupload.util.zip;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * zip 解压模板：业务规则都在这，具体怎么读条目由子类实现。
 *
 * <p>解压规则（保证解压后是「一个父目录」，而不是把文件散落到目标目录里）：
 * <ol>
 *     <li>所有条目共享同一个一级目录时，保留该目录名，解压为 target/一级目录/...</li>
 *     <li>文件直接躺在压缩包根下时，用「压缩包文件名」兜一个父目录</li>
 *     <li>目标目录已存在同名父目录时，旧目录改名成「目录名-bak-时间」备份，新内容仍占用原名</li>
 * </ol>
 *
 * <p>写入顺序：先解压到目标目录下的隐藏临时目录（.unzip-xxx），成功后再做两步 rename：
 * 旧目录 → 备份名、临时目录 → 正式目录名。解压失败只删临时目录，旧数据不受影响。
 *
 * <p>安全：每个条目 normalize 后必须仍落在解压根目录内（防 zip slip），
 * 条目数与解压后总大小设上限（防 zip bomb）。
 */
public abstract class AbstractZipExtractor implements ZipExtractor {

    /** 解压后总字节上限，防 zip bomb */
    protected static final long MAX_TOTAL_BYTES = 512L * 1024 * 1024;
    /** 条目数上限，防 zip bomb */
    protected static final int MAX_ENTRIES = 100_000;
    /** 同名父目录的历史备份最多保留几份 */
    protected static final int MAX_BACKUPS = 5;

    private static final String BACKUP_MARK = "-bak-";
    private static final DateTimeFormatter SUFFIX_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 扫描条目名并确定文件名编码（UTF-8 / GBK 由实现自己探测）
     */
    protected abstract Scan scan(Path zipFile) throws IOException;

    /**
     * 按已确定的编码重新遍历条目并回调解压
     */
    protected abstract void readEntries(Path zipFile, Charset charset, EntryConsumer consumer) throws IOException;

    @Override
    public final Result extract(Path zipFile, Path targetDir, String rootNameHint) throws IOException {
        Path absTarget = targetDir.toAbsolutePath().normalize();
        Files.createDirectories(absTarget);

        Scan scan = scan(zipFile);
        if (scan.names.isEmpty()) {
            throw new IOException("压缩包内没有任何条目: " + zipFile.getFileName());
        }

        String commonTop = commonTopLevel(scan.names);
        String hint = rootNameHint == null || rootNameHint.trim().isEmpty()
                ? zipFile.getFileName().toString()
                : rootNameHint;
        String rootName = commonTop != null ? commonTop : safeRootName(hint);
        Path rootDir = absTarget.resolve(rootName);

        // 先解压到同文件系统下的隐藏临时目录，成功后再切换
        Path workDir = Files.createTempDirectory(absTarget, ".unzip-");
        Path backupDir = null;
        int[] fileCount = {0};
        long[] totalBytes = {0L};
        int[] entryCount = {0};

        try {
            final String topPrefix = commonTop == null ? null : commonTop + "/";
            final int[] entries = entryCount;
            final int[] files = fileCount;
            final long[] bytes = totalBytes;

            readEntries(zipFile, scan.charset, (rawName, directory, in) -> {
                if (++entries[0] > MAX_ENTRIES) {
                    throw new IOException("压缩包条目数超过限制: " + MAX_ENTRIES);
                }
                String name = normalizeName(rawName);
                // 有公共父目录时剥掉它（父目录名由 rootDir 承载），否则整条路径原样保留
                String relative = topPrefix != null && name.startsWith(topPrefix)
                        ? name.substring(topPrefix.length())
                        : name;
                if (relative.isEmpty() || ".".equals(relative)) {
                    return;
                }

                Path out = workDir.resolve(relative).normalize();
                if (!out.startsWith(workDir)) {
                    throw new IOException("检测到非法解压路径(zip slip): " + rawName);
                }

                if (directory || name.endsWith("/")) {
                    Files.createDirectories(out);
                    return;
                }

                Files.createDirectories(out.getParent());
                long written = copyEntry(in, out, bytes[0]);
                bytes[0] += written;
                files[0]++;
            });

            backupDir = backupIfExists(rootDir);
            try {
                Files.move(workDir, rootDir, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException moveError) {
                // 切换失败，把旧目录搬回来，保持现场
                restoreBackup(backupDir, rootDir);
                throw moveError;
            }
            pruneBackups(absTarget, rootName);
            return new Result(rootDir, fileCount[0], totalBytes[0], backupDir);
        } catch (IOException | RuntimeException ex) {
            deleteQuietly(workDir);
            throw ex;
        }
    }

    private static long copyEntry(InputStream in, Path out, long currentTotal) throws IOException {
        long written = 0L;
        byte[] buffer = new byte[8192];
        try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(out,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE))) {
            int len;
            while ((len = in.read(buffer)) > 0) {
                os.write(buffer, 0, len);
                written += len;
                if (currentTotal + written > MAX_TOTAL_BYTES) {
                    throw new IOException("解压后总大小超过限制: " + MAX_TOTAL_BYTES + " 字节");
                }
            }
        }
        return written;
    }

    /**
     * 求所有条目共享的一级目录；有文件直接位于压缩包根下（或路径非法）时返回 null。
     */
    private static String commonTopLevel(List<String> names) {
        String top = null;
        for (String raw : names) {
            String name = normalizeName(raw);
            if (name.isEmpty()) {
                continue;
            }
            if (name.startsWith("/")) {
                return null;
            }
            int idx = name.indexOf('/');
            if (idx <= 0) {
                // 例如 "readme.txt"，说明压缩包没有统一的父目录
                return null;
            }
            String segment = name.substring(0, idx);
            if (top == null) {
                top = segment;
            } else if (!top.equals(segment)) {
                return null;
            }
        }
        return top;
    }

    /**
     * 同名目录存在时，把它改名成「目录名-bak-时间」，返回备份路径；不存在返回 null。
     */
    private static Path backupIfExists(Path rootDir) throws IOException {
        if (!Files.exists(rootDir, LinkOption.NOFOLLOW_LINKS)) {
            return null;
        }
        Path backup = uniqueBackupDir(rootDir);
        Files.move(rootDir, backup, StandardCopyOption.ATOMIC_MOVE);
        return backup;
    }

    private static Path uniqueBackupDir(Path rootDir) {
        String base = rootDir.getFileName().toString() + BACKUP_MARK
                + LocalDateTime.now().format(SUFFIX_FORMAT);
        Path candidate = rootDir.resolveSibling(base);
        int index = 1;
        while (Files.exists(candidate, LinkOption.NOFOLLOW_LINKS)) {
            candidate = rootDir.resolveSibling(base + "-" + index++);
        }
        return candidate;
    }

    private static void restoreBackup(Path backupDir, Path rootDir) {
        if (backupDir == null) {
            return;
        }
        try {
            Files.move(backupDir, rootDir, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ignored) {
            // 尽力恢复，失败时备份仍留在磁盘上
        }
    }

    /**
     * 只保留最近 MAX_BACKUPS 份备份，避免反复上传把磁盘堆满。
     * 只匹配「目录名-bak-14位时间(可选-序号)」，不会误删人工创建的目录。
     */
    private static void pruneBackups(Path targetDir, String rootName) {
        Pattern pattern = Pattern.compile(Pattern.quote(rootName) + "-bak-\\d{14}(-\\d+)?");
        List<Path> backups = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetDir)) {
            for (Path path : stream) {
                if (pattern.matcher(path.getFileName().toString()).matches()) {
                    backups.add(path);
                }
            }
        } catch (IOException ignored) {
            return;
        }
        backups.sort(Comparator.comparing(path -> path.getFileName().toString()));
        for (int i = 0; i < backups.size() - MAX_BACKUPS; i++) {
            deleteQuietly(backups.get(i));
        }
    }

    private static String normalizeName(String rawName) {
        if (rawName == null) {
            return "";
        }
        String name = rawName.replace('\\', '/');
        while (name.startsWith("./")) {
            name = name.substring(2);
        }
        return name;
    }

    private static String stripZipSuffix(String fileName) {
        String name = fileName == null ? "" : fileName;
        int dot = name.lastIndexOf('.');
        if (dot > 0) {
            name = name.substring(0, dot);
        }
        name = name.replace('/', '_').replace('\\', '_').trim();
        if (name.isEmpty() || ".".equals(name) || "..".equals(name)) {
            return "unzipped";
        }
        return name;
    }

    /**
     * 把客户端传来的文件名洗成安全的目录名：只取最后一段、去掉扩展名与控制字符。
     */
    private static String safeRootName(String rawName) {
        String name = rawName == null ? "" : rawName;
        int slash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        if (slash >= 0) {
            name = name.substring(slash + 1);
        }
        return stripZipSuffix(name.replaceAll("[\\p{Cntrl}]+", "_").trim());
    }

    private static void deleteQuietly(Path rootDir) {
        if (!Files.exists(rootDir)) {
            return;
        }
        try {
            Files.walk(rootDir)
                    .sorted((a, b) -> b.getNameCount() - a.getNameCount())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                            // 尽力清理
                        }
                    });
        } catch (IOException ignored) {
            // 尽力清理
        }
    }

    /**
     * 条目扫描结果：确定下来的文件名编码 + 条目名列表
     */
    protected static final class Scan {
        private final Charset charset;
        private final List<String> names;

        protected Scan(Charset charset, List<String> names) {
            this.charset = charset;
            this.names = names;
        }
    }

    /**
     * 条目回调。目录条目时 in 为 null，非目录时 in 只能读到当前条目末尾，不要关闭它。
     */
    @FunctionalInterface
    protected interface EntryConsumer {
        void accept(String name, boolean directory, InputStream in) throws IOException;
    }
}
