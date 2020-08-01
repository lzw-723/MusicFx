/*
 * @Author: lzw-723
 * @Date: 2020-04-12 15:59:33
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-07-31 16:39:59
 * @Description: 艺术家工具类
 */
package io.github.lzw.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.bean.Artist;
import io.github.lzw.bean.SongL;

public class ArtistUtil {
    private static final Logger logger = LoggerFactory.getLogger(ArtistUtil.class);

    private static final Map<String, Boolean> map = new HashMap<>();
    private static final List<Artist> artists = new ArrayList<>();
    private static final Map<String, List<SongL>> map_song = new HashMap<>();

    public static List<Artist> getArtists() {
        if (artists.size() > 0) {
            return artists;
        }
        map.clear();
        artists.clear();
        SongUtil.getSongs().forEach(song -> {
            try {
                for (String str : song.getArtist().split("/")) {
                    if (map.get(str) == null) {
                        artists.add(new Artist(str, SongInfoUtil.getArtistCover(new File(new URI(song.getUri())))));
                    }
                    map.put(song.getArtist(), true);
                }

            } catch (URISyntaxException e) {
                logger.error("艺术家列表获取失败(URI错误)", e);
            }
            catch (IllegalArgumentException e){
                logger.error("艺术家列表获取失败", e);
            }
        });
        return artists;
    }

    public static List<SongL> getSongs(Artist artist) {
        if (map_song.get(artist.getName()) != null) {
            return map_song.get(artist.getName());
        }
        List<SongL> songs = new ArrayList<>();
        SongUtil.getSongs().forEach(song -> {
            if (song.getArtist().indexOf(artist.getName()) != -1) {
                songs.add(song);
            }

        });
        map_song.put(artist.getName(), songs);
        logger.info("{}歌单完成获取", artist.getName());
        return songs;
    }
}