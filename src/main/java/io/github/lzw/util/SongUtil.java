/*
 * @Author: lzw-723
 * @Date: 2020-02-02 13:32:29
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-13 12:20:16
 * @Description: 本地歌曲工具类
 */
package io.github.lzw.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.github.lzw.Config;
import io.github.lzw.bean.SongL;

public class SongUtil {
    private static final List<SongL> songs = new ArrayList<>();
    public static List<SongL> getSongs() {
        File dir = new File(Config.getInstance().getDir());
        songs.clear();
        getSongs(dir, true);
        return songs;
    }

    public static List<SongL> getSongsIgnoreCache() {
        File dir = new File(Config.getInstance().getDir());
        songs.clear();
        getSongs(dir, false);
        return songs;
    }

    private synchronized static void getSongs(File dir, boolean cacheable) {
        File ser = FileUtil.getFile("songs.json");
        if (ser.exists() && cacheable) {
            read(ser);
        } else {
            if (dir.exists() && dir.listFiles().length > 0)
                for (File file : dir.listFiles()) {
                    String path = file.getAbsolutePath().toLowerCase();
                    if (path.endsWith(".mp3") || path.endsWith(".wav") || path.endsWith(".wma")) {
                        songs.add(new SongL(file.toPath().toUri().toString()));
                    }
                }
            if (songs.size() > 0) {
                save(ser);
            } else {
                ser.delete();
            }
        }

    }

    private static void save(File file) {
        String data = JSON.toJSONString(songs);
        FileUtil.writeStringToFile(file, data);
    }

    private static List<SongL> read(File file) {
        songs.clear();
        songs.addAll(JSON.parseArray(FileUtil.readFileToString(file), SongL.class));
        return songs;
    }
}
