/*
 * @Author: lzw-723
 * @Date: 2020-03-25 14:58:16
 * @LastEditTime: 2020-04-13 16:22:02
 * @LastEditors: lzw-723
 * @Description: 启动类(Java-11，非模块化，需要将OpenJfx打包进Jar)
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\Main.java
 */
package io.github.lzw;

import javafx.application.Application;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}