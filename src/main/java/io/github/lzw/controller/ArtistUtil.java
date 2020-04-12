/*
 * @Author: lzw-723
 * @Date: 2020-04-12 15:59:33
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-12 16:31:45
 * @Description: 艺术家工具类
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\controller\ArtistUtil.java
 */
package io.github.lzw.controller;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.github.lzw.bean.Artist;
import io.github.lzw.util.SongInfoUtil;
import io.github.lzw.util.SongUtil;

public class ArtistUtil {

    private static final List<Artist> artists = new ArrayList<>();

    public static List<Artist> getArtists() {
        artists.clear();
        SongUtil.getSongs().forEach(song -> {
            try {
                artists.add(
                        new Artist(song.getArtist(), SongInfoUtil.getArtistCover(new File(new URI(song.getUri())))));
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        return artists;
    }
}