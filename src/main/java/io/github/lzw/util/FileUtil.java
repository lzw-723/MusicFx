/*
 * @Author: lzw-723
 * @Date: 2020-04-09 19:54:03
 * @LastEditTime: 2021-02-10 20:02:47
 * @LastEditors: Please set LastEditors
 * @Description: 文件工具类
 */
package io.github.lzw.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.SystemInfo;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * @description: 获取当前Jar所在目录
     * @return: file
     */
    public static File getJarDir() {
        return new File(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile();
    }

    /**
     * 获取user目录下.MusicFX(App目录)
     */    
    public static File getAppDir() {
        File appDir = new File(SystemInfo.userHome(), ".MusicFX");
        checkDir(appDir);
        return appDir;
    }

    /**
     * 获取user目录名为child的文件 ,如不存在则新建
     * 
     * @param child 文件名
     * @return: file
     */
    public static File getFileOnAppDir(String child) {
        File file = new File(getAppDir(), child);
        checkDir(file.getParentFile());
        checkFile(file);
        return file;
    }

    /**
     * 获取user目录名为dir的目录 ,如不存在则新建
     * 
     * @param child 目录名
     * @return: file
     */
    public static File getDirOnAppDir(String dir) {
        File file = new File(getAppDir(), dir);
        checkDir(file);
        return file;
    }
    public static boolean checkFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("新建文件{}失败，{}", file.getName(), e.getMessage());
            }
        }
        return file.exists();
    }

    public static boolean checkDir(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.exists();
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