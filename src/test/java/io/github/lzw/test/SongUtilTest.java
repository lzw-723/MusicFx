/*
 * @Author: lzw-723
 * @Date: 2020-10-31 13:31:38
 * @LastEditTime: 2021-01-01 16:51:21
 * @Description: SongUtil测试类
 */
package io.github.lzw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.lzw.util.SongUtil;

@RunWith(MockitoJUnitRunner.class)
public class SongUtilTest {
    
    @Test
    public void distinguishMusicFiles() {
        File file = mock(File.class);

        when(file.getName()).thenReturn("a.mp3");
        assertTrue(SongUtil.verifyFileSuffix(file));

        when(file.getName()).thenReturn("a.wav");
        assertTrue(SongUtil.verifyFileSuffix(file));

        when(file.getName()).thenReturn("a.wma");
        assertTrue(SongUtil.verifyFileSuffix(file));

        when(file.getName()).thenReturn("a.mp4");
        assertFalse(SongUtil.verifyFileSuffix(file));
        
        when(file.getName()).thenReturn("amp3");
        assertFalse(SongUtil.verifyFileSuffix(file));
    }
    @Test
    public void scanTest() {
        File dir = mock(File.class);
        File file;
        File[] files = {};
        when(dir.listFiles()).thenReturn(null);
        
        SongUtil.scanSongs(dir);
    }
}
