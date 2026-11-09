package com.lx.demo.springbootfileupload.util.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 稳定方案：Apache Commons Compress。
 *
 * <p>相比 JDK 自带 java.util.zip 多出来的能力：Zip64、bzip2/deflate64/zstd/lzma 等压缩方法、
 * 中央目录遍历（可重复读、能读 self-extracting 前缀）、编码显式指定。
 *
 * <p>编码探测不靠猜异常：先从中央目录取条目名的「原始字节」，用严格解码器依次试
 * UTF-8 / GBK，哪個能完整解码就用哪个（commons-compress 自身解码失败时会替换成 '?'，
 * 直接看结果会误判）。
 *
 * <p>它只负责「读 zip」，父目录保留、旧目录备份、原子切换、zip slip 校验仍在
 * {@link AbstractZipExtractor} 里统一实现。
 */
public class CommonsCompressZipExtractor extends AbstractZipExtractor {

    private static final Charset GBK = Charset.forName("GBK");
    /** 嗅探用编码：任何字节都能 1:1 解码，保证打开不会因文件名报错 */
    private static final Charset SNIFF_CHARSET = StandardCharsets.ISO_8859_1;

    @Override
    protected Scan scan(Path zipFile) throws IOException {
        List<byte[]> rawNames = new ArrayList<>();
        try (ZipFile zip = open(zipFile, SNIFF_CHARSET)) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                byte[] rawName = entry.getRawName();
                rawNames.add(rawName != null ? rawName : entry.getName().getBytes(SNIFF_CHARSET));
            }
        }

        Charset charset = detectCharset(rawNames);
        return new Scan(charset, readNames(zipFile, charset));
    }

    @Override
    protected void readEntries(Path zipFile, Charset charset, EntryConsumer consumer) throws IOException {
        try (ZipFile zip = open(zipFile, charset)) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    consumer.accept(entry.getName(), true, null);
                } else {
                    try (InputStream in = zip.getInputStream(entry)) {
                        consumer.accept(entry.getName(), false, in);
                    }
                }
            }
        }
    }

    /**
     * 第一个能严格解码所有条目名的编码，UTF-8 优先，兜底 GBK
     */
    private static Charset detectCharset(List<byte[]> rawNames) {
        for (Charset charset : new Charset[]{StandardCharsets.UTF_8, GBK}) {
            if (isStrictlyDecodable(rawNames, charset)) {
                return charset;
            }
        }
        return GBK;
    }

    private static boolean isStrictlyDecodable(List<byte[]> rawNames, Charset charset) {
        CharsetDecoder decoder = charset.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);
        for (byte[] rawName : rawNames) {
            try {
                decoder.reset();
                decoder.decode(ByteBuffer.wrap(rawName));
            } catch (CharacterCodingException ex) {
                return false;
            }
        }
        return true;
    }

    private static List<String> readNames(Path zipFile, Charset charset) throws IOException {
        List<String> names = new ArrayList<>();
        try (ZipFile zip = open(zipFile, charset)) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            while (entries.hasMoreElements()) {
                names.add(entries.nextElement().getName());
            }
        }
        return names;
    }

    private static ZipFile open(Path zipFile, Charset charset) throws IOException {
        return ZipFile.builder()
                .setPath(zipFile)
                .setCharset(charset)
                .get();
    }
}
