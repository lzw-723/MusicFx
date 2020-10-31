/*
 * @Author: lzw-723
 * @Date: 2020-02-02 13:32:29
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2020-10-31 14:58:09
 * @Description: 本地歌曲工具类
 */
package io.github.lzw.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 验证文件后缀是否为音乐文件（mp3 wav wma) 
     * @param file
     * @return boolean
     */
    public static boolean verifyFileSuffix(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".wma");
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
                    if (verifyFileSuffix(file) && SongInfoUtil.checkAvailable(file)) {
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

    public static List<SongL> scanSongs(File dir) {
        return Arrays.asList(dir.listFiles()).parallelStream().filter(SongUtil::verifyFileSuffix).map(file -> file.toPath().toUri().toString()).map(SongL::new).collect(Collectors.toList());
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
