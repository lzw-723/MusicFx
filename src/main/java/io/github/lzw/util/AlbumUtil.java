/*
 * @Author: lzw-723
 * @Date: 2020-04-14 15:38:55
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-14 16:03:12
 * @Description: 专辑工具类
 */
package io.github.lzw.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.bean.Album;
import io.github.lzw.bean.SongL;

public class AlbumUtil {
    private static final Logger logger = LoggerFactory.getLogger(AlbumUtil.class);
    private static final Map<String, List<SongL>> map = new HashMap<>();
    private static final Map<String, Boolean> albumsCache = new HashMap<>();
    private static final List<Album> list = new ArrayList<>();

    public static List<Album> getAlbums() {
        if (list.size() != 0) {
            return list;
        }
        list.clear();
        SongUtil.getSongs().forEach(song -> {
            if (albumsCache.get(song.getAlbum()) == null) {
                list.add(new Album(song.getAlbum(), song.getArtwork()));
                albumsCache.put(song.getAlbum(), true);
            }
        });
        return list;
    }

    public static List<SongL> getSongs(Album album) {
        List<SongL> songs = new ArrayList<>();
        if (map.get(album.getName()) != null) {
            songs.addAll(map.get(album.getName()));
        } else {
            SongUtil.getSongs().forEach(song -> {
                if (song.getAlbum().equals(album.getName())) {
                    songs.add(song);
                }
            });
            logger.info("专辑{}获取完成,", album.getName());
        }

        return songs;
    }
}