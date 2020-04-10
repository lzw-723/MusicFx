/*
 * @Author: lzw-723
 * @Date: 2020-04-09 19:54:03
 * @LastEditTime: 2020-04-10 09:51:03
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

    /**
     * @description: 获取当前Jar所在目录
     * @return: file
     */    
    public static File getDir() {
        return new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile();
    }

    /**
     * @description: 获取当前Jar所在目录名为child的文件 
     * @param 文件名 
     * @return: file
     */    
    public static File getFile(String child) {
        return new File(getDir(), child);
    }

    public static String readFileToString(File file) {
        try {
            return FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            logger.error("文件读取失败-{}-{}", file.getName(), e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeStringToFile(File file, String data) {
        try {
            FileUtils.writeStringToFile(file, data, "utf-8");
            return true;
        } catch (IOException e) {
            logger.error("文件读取失败-{}-{}", file.getName(), e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}