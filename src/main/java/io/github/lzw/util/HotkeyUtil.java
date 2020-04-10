/*
 * @Author: lzw-723
 * @Date: 2020-04-10 10:28:40
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-10 10:32:47
 * @Description: 快捷键工具类
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\\util\HotkeyUtil.java
 */
package io.github.lzw.util;

import io.github.lzw.Config;
import io.github.lzw.core.MusicFx;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class HotkeyUtil {

    /**
     * @description: 快捷键绑定
     * @param scene
     */
    public static void initHotKey(Scene scene) {
        // Ctrl + P 暂停或播放
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN),
                () -> {
                    if (Config.getInstance().getHotkeyAble()) {
                    MusicFx.get().playOrPause();
                }
            });
        // Ctrl + Left 上一首
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHORTCUT_DOWN),
                () -> {
                    if (Config.getInstance().getHotkeyAble()) {
                    MusicFx.get().previous();
                }
            });
        // Ctrl + Right 下一首
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_DOWN),
                () -> {
                    if (Config.getInstance().getHotkeyAble()) {
                    MusicFx.get().next();
                }
            });
    }
}