/*
 * @Author: lzw-723 
 * @Date: 2020-03-23 15:26:03 
 * @Last Modified by: lzw-723
 * @Last Modified time: 2020-03-24 10:54:07
 * @Description: 在线音乐搜索服务
 */
package io.github.lzw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import io.github.lzw.api.SongApi;
import io.github.lzw.bean.SongBean;
import io.github.lzw.bean.SongBean.SongBeanO;
import io.github.lzw.bean.SongO;
import retrofit2.Retrofit;

public class SongOService {
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.songe.cc/")
            .addConverterFactory(FastJsonConverterFactory.create()).build();

    public static void getSongOs(String input, Type type, CallBack callBack) {
        new Thread(() -> {

            List<SongO> songs = new ArrayList<>();
            try {
                List<SongBean.SongBeanO> songBOs = retrofit.create(SongApi.class)
                        .getSongs(input, "name", type.getType(), 1).execute().body().getData();
                if (songBOs.size() == 10) {
                    songBOs.addAll(retrofit.create(SongApi.class).getSongs(input, "name", type.getType(), 2).execute()
                            .body().getData());
                }
                for (SongBeanO songBeanO : songBOs) {
                    songs.add(new SongO(songBeanO));
                }
                callBack.onSuccess(songs);
            } catch (IOException e) {
                callBack.onError(e);
                e.printStackTrace();
            }

        }).start();

    }

    public enum Type {
        Kugou("kugou", "酷狗"), Netease("netease", "网易"), Baidu("baidu", "百度"), QQ("qq", "QQ"), Kuwo("kuwo", "酷我");

        private String type;
        private String name;

        private Type(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static Type stringOf(String typename) {
            Type type = Netease;
            switch (typename) {
                case "网易":
                    type = Netease;
                    break;
                case "百度":
                    type = Baidu;
                    break;
                case "酷狗":
                    type = Kugou;
                    break;
                case "酷我":
                    type = Kuwo;
                    break;
                case "QQ":
                    type = QQ;
                    break;
            }
            return type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public static interface CallBack {
        void onSuccess(List<SongO> songs);

        void onError(IOException e);
    }
}