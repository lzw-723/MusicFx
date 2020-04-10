/*
 * @Author: lzw-723
 * @Date: 2020-02-02 13:32:29
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-10 08:53:35
 * @Description: 描述信息
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\\util\SongUtil.java
 */
package io.github.lzw.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.github.lzw.Config;
import io.github.lzw.FileUtil;
import io.github.lzw.bean.SongL;

public class SongUtil {
    public static List<SongL> getSongs() {
        File dir = new File(Config.getInstance().getDir());
        return getSongs(dir, true);
    }

    public static List<SongL> getSongsIgnoreCache() {
        File dir = new File(Config.getInstance().getDir());
        return getSongs(dir, false);
    }

    private synchronized static List<SongL> getSongs(File dir, boolean cacheable) {
        File ser = FileUtil.getFile("songs.json");
        if (ser.exists() && cacheable) {
            return read(ser);
        } else {
            List<SongL> list = new ArrayList<>();
            if (dir.exists() && dir.listFiles().length > 0)
                for (File file : dir.listFiles()) {
                    String path = file.getAbsolutePath().toLowerCase();
                    if (path.endsWith(".mp3") || path.endsWith(".wav") || path.endsWith(".wma")) {
                        list.add(new SongL(file.toPath().toUri().toString()));
                    }
                }
            if (list.size() > 0) {
                save(list, ser);
            } else {
                ser.delete();
            }
            return list;
        }

    }

    private static void save(List<SongL> list, File file) {
        String data = JSON.toJSONString(list);
        FileUtil.writeStringToFile(file, data);
    }

    private static List<SongL> read(File file) {
        List<SongL> songs = JSON.parseArray(FileUtil.readFileToString(file), SongL.class);
        return songs;
    }
}
