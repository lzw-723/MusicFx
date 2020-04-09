/*
 * @Author: lzw-723
 * @Date: 2020-04-09 11:16:21
 * @LastEditTime: 2020-04-09 11:24:49
 * @LastEditors: Please set LastEditors
 * @Description: 日志上报服务
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\service\LogService.java
 */
package io.github.lzw.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import io.github.lzw.api.LogApi;
import retrofit2.Retrofit;

public class LogService {
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://lzw-723.now.cc").build();

    public static boolean sendLog() {
        File logFile = new File("./musicfx-log.log");
        if (logFile.exists()) {
            String response;
            try {
                response = retrofit.create(LogApi.class).send(FileUtils.readFileToString(logFile)).execute().body()
                        .string();
            } catch (IOException e) {
                return false;
            }
        if (response.indexOf("成功") != -1) {
            return true;
        }
        }
        return false;
    }
}