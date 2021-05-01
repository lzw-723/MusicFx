/*
 * @Author: lzw-723
 * @Date: 2020-04-10 10:28:40
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-10 14:42:39
 * @Description: 快捷键工具类
 */
package io.github.lzw.util;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.LoggerFactory;

import io.github.lzw.Config;
import io.github.lzw.core.MusicFXSingleton;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class HotkeyUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HotkeyUtil.class);

    /**
     * @description: 快捷键绑定 (当前窗口 + 全局 + 自动解绑)
     * @param scene
     * @return:
     */
    public static void registerHotkey(Scene scene, Stage stage) {
        registerSceneHotKey(scene);
        registerGlobalHotkey();
        // 窗口关闭自动解绑
        stage.setOnCloseRequest(event -> unregisterHotkey(scene));
    }

    /**
     * @description: 当前窗口快捷键绑定
     * @param scene
     */
    private static void registerSceneHotKey(Scene scene) {
        // Ctrl + P 暂停或播放
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN), () -> {
            if (Config.getInstance().getHotkeyAble()) {
                MusicFXSingleton.get().playOrPause();
            }
        });
        // Ctrl + Left 上一首
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHORTCUT_DOWN), () -> {
            if (Config.getInstance().getHotkeyAble()) {
                MusicFXSingleton.get().previous();
            }
        });
        // Ctrl + Right 下一首
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_DOWN), () -> {
            if (Config.getInstance().getHotkeyAble()) {
                MusicFXSingleton.get().next();
            }
        });
    }

    /**
     * @description: 全局快捷键绑定
     */
    private static void registerGlobalHotkey() {
        try {
            // 关闭jnativehook日志输出
            Logger.getLogger("org.jnativehook").setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            logger.error("全局快捷键绑定失败，{}", ex.getMessage());
            ex.printStackTrace();
        }
        HashMap<Integer, Boolean> keys = new HashMap<>();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

            @Override
            public void nativeKeyTyped(NativeKeyEvent e) {

            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
                // 松开按键
                if (keys.get(e.getKeyCode()) != null || keys.get(e.getKeyCode())) {
                    keys.put(e.getKeyCode(), false);
                }
            }

            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                // 按下按键
                if (keys.get(e.getKeyCode()) == null || !keys.get(e.getKeyCode())) {
                    keys.put(e.getKeyCode(), true);
                }

                if (!Config.getInstance().getHotkeyAble()) {
                    return;
                }
                
                // 按下 Ctrl
                if (keys.get(NativeKeyEvent.VC_CONTROL) != null && keys.get(NativeKeyEvent.VC_CONTROL)) {

                    // 按下 Alt
                    if (keys.get(NativeKeyEvent.VC_ALT) != null && keys.get(NativeKeyEvent.VC_ALT)) {

                        // 按下 P
                        if (keys.get(NativeKeyEvent.VC_P) != null && keys.get(NativeKeyEvent.VC_P)) {
                            MusicFXSingleton.get().playOrPause();
                        }

                        // 按下 Left
                        if (keys.get(NativeKeyEvent.VC_LEFT) != null && keys.get(NativeKeyEvent.VC_LEFT)) {
                            MusicFXSingleton.get().previous();
                        }

                        // 按下 Right
                        if (keys.get(NativeKeyEvent.VC_RIGHT) != null && keys.get(NativeKeyEvent.VC_RIGHT)) {
                            MusicFXSingleton.get().next();
                        }
                    }
                }
            }
        });
    }

    /**
     * @description: 解绑快捷键
     * @param scene
     */
    public static void unregisterHotkey(Scene scene) {
        unregisterSceneHotkey(scene);
        unregisterGlobalHotkey();
    }

    /**
     * @description: 解绑当前窗口快捷键 
     * @param scene
     */    
    private static void unregisterSceneHotkey(Scene scene) {
        scene.getAccelerators().clear();
    }

    /**
     * @description: 解绑全局快捷键
     */    
    private static void unregisterGlobalHotkey() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            logger.error("全局快捷键解绑失败，{}", e.getMessage());
            e.printStackTrace();
        }
    }

}