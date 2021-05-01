/*
 * @Author: your name
 * @Date: 2020-04-17 10:25:02
 * @LastEditTime: 2021-01-01 16:25:37
 * @LastEditors: Please set LastEditors
 * @Description: 界面抽象
 * @FilePath: \MusicFXSingleton\src\main\java\io\github\lzw\controller\ControllerImpl.java
 */
package io.github.lzw.controller;

import io.github.lzw.bean.Song;

public interface ControllerImpl {
    String getTitle();
    ControllerImpl getController();
    void onPlay(Song song);
}