/*
 * @Author: lzw-723
 * @Date: 2020-03-28 15:16:32
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-09 21:34:38
 * @Description: 系统信息类
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\SystemInfo.java
 */

package io.github.lzw;

public class SystemInfo {

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}