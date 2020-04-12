/*
 * @Author: lzw-723
 * @Date: 2020-02-02 15:16:06
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-12 15:58:24
 * @Description: 本地歌曲
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\bean\SongL.java
 */
package io.github.lzw.bean;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import io.github.lzw.util.SongInfoUtil;

public class SongL extends Song {

    public SongL(String uri) {
        try {
            File file = new File(new URI(uri));
            setUri(uri);
            setTitle(SongInfoUtil.getTitle(file));
            setArtist(SongInfoUtil.getArtist(file));
            setAlbum(SongInfoUtil.getAlbum(file));
            setLength(SongInfoUtil.getLength(file));
            setArtwork(SongInfoUtil.getArtWork(file));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 供FastJson反序列化使用
    public SongL() {
    }
}
