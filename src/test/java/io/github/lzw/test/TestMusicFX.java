/*
 * @Author: lzw-723
 * @Date: 2020-04-13 10:11:02
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-13 10:59:17
 * @Description: 描述信息
 * @FilePath: \MusicFx\src\test\java\io\github\lzw\test\TestMusicFX.java
 */
package io.github.lzw.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.junit.Test;

import io.github.lzw.util.SongInfoUtil;

public class TestMusicFX {
    @Test
    public void TestTag() {
        AudioFile audioFile = SongInfoUtil.getAudioFile(new File("D:\\Music\\Animenz - ユキトキ - 我的青春恋爱物语果然有问题 OP.mp3"));
        int l = 0;
        assertTrue(l == 0);
    }
}