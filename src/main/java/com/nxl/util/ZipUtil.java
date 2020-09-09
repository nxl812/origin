package com.nxl.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;

import java.io.*;
import java.util.List;

public class ZipUtil {


    public static void main(String[] args) {
        String targetPath = "/tmp/nxl/";
        unZipToTargetPath(new File("D:\\project\\origin\\2.zip"), targetPath, "");
    }

    public static void unZipToTargetPath(File source, String targetPath, String password) {
        try {
            ZipFile zFile = new ZipFile(source);
            zFile.setFileNameCharset("GBK");
            File destDir = new File(targetPath + source.getName().substring(0, source.getName().lastIndexOf(".")) + File.separator);
            destDir.mkdir();
            // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
            if (!zFile.isValidZipFile()) {
                throw new ZipException("压缩文件不合法,可能被损坏.");
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(password.toCharArray());
            }
            List<FileHeader> list = zFile.getFileHeaders();
            for (FileHeader fileHeader : list) {
                if (!fileHeader.isDirectory()) {
                    ZipInputStream inputStream = zFile.getInputStream(fileHeader);
                    if (fileHeader.getFileName().endsWith(".zip")
                            || fileHeader.getFileName().endsWith(".rar")
                            || fileHeader.getFileName().endsWith(".7z")) {
                        File tempFile = new File(destDir.getCanonicalPath() + File.separator + fileHeader.getFileName());
                        inputStream2File(inputStream, tempFile);
                        unZipToTargetPath(tempFile, destDir.getCanonicalPath() + File.separator, "");
                        tempFile.delete();
                    } else {
                        File tempFile = new File(destDir.getCanonicalPath()+fileHeader.getFileName());
                        inputStream2File(inputStream, tempFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void inputStream2File(InputStream is, File file) throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } finally {
            is.close();
        }
    }

}
