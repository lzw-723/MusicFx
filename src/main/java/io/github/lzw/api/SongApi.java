package io.github.lzw.api;

import io.github.lzw.bean.SongBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * SongUtilOnline
 */
public interface SongApi {
    @Headers({ "Host: www.songe.cc", "Connection: keep-alive", "Origin: http://www.songe.cc",
            "X-Requested-With: XMLHttpRequest",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69",
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8",
            "Referer: http://www.songe.cc/?name=behind&type=netease" })
    @FormUrlEncoded
    @POST(".")
    Call<SongBean> getSongs(@Field("input") String input, @Field("filter") String filter, @Field("type") String type,
            @Field("page") int page);
}