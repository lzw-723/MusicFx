package lzw.musicol.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lzw.util.network.HttpRequester;

import java.util.ArrayList;
import java.util.List;

public class MusicApi {
    static String[] types = new String[]{"qq", "baidu", "kugou", "netease", "kuwo", "5singyc"};

    public static List<Song> getSongs(String q) {
        return getSongs(q, 3);
    }

    public static List<Song> getSongs(String q, int type) {

        List<Song> songs = new ArrayList<>();
        HttpRequester.Builder builder = new HttpRequester.Builder();
        builder.setUrl(Setting.getApi());
        builder.addData("input", q);
        builder.addData("filter", "name");
        builder.addData("type", types[type]);
        builder.addData("page", "1");
        builder.setCache(false);
        builder.setAsync(false);
        builder.addHead("X-Requested-With", "XMLHttpRequest");
        builder.setReferer("Referer: " + Setting.getApi());
        builder.post(new HttpRequester.Listener() {
            @Override
            public void onSuccess(byte[] result) {
                System.out.println(new String(result));
                JSONObject jsonObject = JSON.parseObject(new String(result));
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject songJson = jsonArray.getJSONObject(i);
                    System.out.println(songJson.toString());
                    Song song = new Song(songJson.getString("title"), songJson.getString("author"), songJson.getString("pic"), songJson.getString("url"));
                    songs.add(song);
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
        return songs;
    }
}
