/*
 * @Author: lzw-723
 * @Date: 2020-04-09 19:54:03
 * @LastEditTime: 2020-04-10 08:51:54
 * @LastEditors: lzw-723
 * @Description: 文件工具类
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\FileUtil.java
 */
package io.github.lzw;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static File getDir() {
        return new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile());
    }

    public static File getFile(String child) {
        return new File(getDir(), child);
    }

    public static String readFileToString(File file) {
        try {
            return FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            logger.error("文件读取失败", file.getAbsolutePath());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeStringToFile(File file, String data) {
        try {
            FileUtils.writeStringToFile(file, data, "utf-8");
            return true;
        } catch (IOException e) {
            logger.error("文件读取失败", file.getAbsolutePath());
            e.printStackTrace();
        }
        return false;
    }
}