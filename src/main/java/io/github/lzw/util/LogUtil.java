/*
 * @Author: lzw-723
 * @Date: 2020-04-09 15:56:38
 * @LastEditTime: 2020-04-09 21:18:15
 * @LastEditors: lzw-723
 * @Description: 日志工具类
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\\util\LogUtil.java
 */
package io.github.lzw.util;

import io.github.lzw.FileUtil;

public class LogUtil {
    /**
     * @description:获取日志
     * @return:日志文本
     */
    public static String getLog() {
        return FileUtil.readFileToString(FileUtil.getFile("musicfx-log.log"));
    }
}