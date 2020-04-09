/*
 * @Author: lzw-723
 * @Date: 2020-04-09 11:09:49
 * @LastEditTime: 2020-04-09 11:13:38
 * @LastEditors: Please set LastEditors
 * @Description: 日志上报api
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\api\LogApi.java
 */
package io.github.lzw.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface LogApi {
    
    @POST("/log/send.php")
    Call<ResponseBody> send(@Field("content") String content);
}
