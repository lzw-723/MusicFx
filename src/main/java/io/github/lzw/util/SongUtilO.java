/*
 * @Author: lzw-723 
 * @Date: 2020-03-23 15:26:03 
 * @Last Modified by: lzw-723
 * @Last Modified time: 2020-03-24 10:54:07
 */
package io.github.lzw.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import io.github.lzw.api.SongApi;
import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongBean;
import io.github.lzw.bean.SongBean.SongBeanO;
import io.github.lzw.bean.SongO;
import retrofit2.Retrofit;

/**
 * SongUtilO
 */
public class SongUtilO {
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.songe.cc/")
            .addConverterFactory(FastJsonConverterFactory.create()).build();

    public static ArrayList<Song> getSongOs(String input) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            List<SongBean.SongBeanO> songBOs = retrofit.create(SongApi.class).getSongs(input, "name", "netease", 1).execute().body().getData();
            if (songBOs.size() == 10) {
                songBOs.addAll(retrofit.create(SongApi.class).getSongs(input, "name", "netease", 2).execute().body().getData());
            }
            for (SongBeanO songBeanO : songBOs) {
                songs.add(new SongO(songBeanO));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    return songs;
    }
}