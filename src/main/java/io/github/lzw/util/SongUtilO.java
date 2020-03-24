/*
 * @Author: lzw-723 
 * @Date: 2020-03-23 15:26:03 
 * @Last Modified by: lzw-723
 * @Last Modified time: 2020-03-24 10:54:07
 */
package io.github.lzw.util;

import java.io.IOException;
import java.util.ArrayList;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import io.github.lzw.api.SongApi;
import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongO;
import retrofit2.Retrofit;

/**
 * SongUtilO
 */
public class SongUtilO {
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.songe.cc/")
            .addConverterFactory(FastJsonConverterFactory.create()).build();

    public static ArrayList<SongO> getSongOs(String input) {
        ArrayList<SongO> songs = new ArrayList<>();
        try {
            songs.addAll(retrofit.create(SongApi.class).getSongs(input, "name", "netease", 1).execute().body().getData());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    return songs;
    }
    public static Song SongO2song(SongO songO) {
        return new Song(songO);
    }
    private static ArrayList<Song> SongO2song(ArrayList<SongO> lists){
        ArrayList<Song> list = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            list.add(new Song(lists.get(i)));
        }
        return list;
    }
}