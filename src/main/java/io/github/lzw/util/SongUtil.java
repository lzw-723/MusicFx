package io.github.lzw.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.FileUtils;

import io.github.lzw.Config;
import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongL;

public class SongUtil {
    public static List<Song> getSongs() {
        File dir = new File(Config.getInstance().getDir());
        System.out.println(dir.exists());
        return getSongs(dir);
    }

    private static List<Song> getSongs(File dir) {
        File ser = new File(new File(".").getPath(), "songs.json");
        if (ser.exists()) {
            return read(ser);
        } else {
            List<Song> list = new ArrayList<>();
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

    private static void save(List<Song> list, File file) {
        String data = JSON.toJSONString(list);
        try {
            FileUtils.writeStringToFile(file, data);
        } catch (IOException e) {
            Logger.getLogger(SongUtil.class.getSimpleName()).warning("数据保存失败");
            e.printStackTrace();
        }
    }

    private static List<Song> read(File file) {
        List<Song> list = new ArrayList<>();
        try {
            String data = FileUtils.readFileToString(file);
            List<Song> songs = JSON.parseArray(FileUtils.readFileToString(file), Song.class);
            return songs;
        } catch (Exception e) {
            Logger.getLogger(SongUtil.class.getSimpleName()).warning("数据读取失败," + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
