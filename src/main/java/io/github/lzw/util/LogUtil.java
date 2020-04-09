/*
 * @Author: lzw-723
 * @Date: 2020-04-09 15:56:38
 * @LastEditTime: 2020-04-09 17:05:30
 * @LastEditors: Please set LastEditors
 * @Description: 日志工具类
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\\util\LogUtil.java
 */
package io.github.lzw.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class LogUtil {
    public static String getLog() {
        try {
            return FileUtils.readFileToString(new File(new File("."), "/music-log.log"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "日志读取失败";
    }
}