/*
 * @Author: lzw-723
 * @Date: 2020-04-13 10:11:02
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-07-31 16:43:16
 * @Description: 描述信息
 * @FilePath: \MusicFx\src\test\java\io\github\lzw\test\TestMusicFX.java
 */
package io.github.lzw.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class TestMusicFX {
    @Test
    public void TestTag() {
        // File file = new File("D:\\Test\\Animenz - 未完成ストライド - 冰菓 OP2.mp3");
        boolean check = new File("D:\\Test\\e\\Animenz - 未完成ストライド - 冰菓 OP2.mp3").exists();
        int l = 0;
        assertTrue(true);
    }
}