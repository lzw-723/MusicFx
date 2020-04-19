/*
 * @Author: lzw-723
 * @Date: 2020-04-18 09:25:40
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-19 09:12:04
 * @Description: 统计工具类
 */
package io.github.lzw.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.github.lzw.bean.Record;
import io.github.lzw.bean.SongL;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class StatisticsUtil {
    private static final ObservableList<Record> data = FXCollections.observableArrayList();
    static {
        read();
        clearInvalidRecords();
        data.addListener((ListChangeListener<Record>) change -> save());
    }

    public static void add(SongL songL) {
        data.add(new Record(new Date(), songL));
    }

    public static List<Record> getRecords() {
        return new ArrayList<>(data);
    }

    private static void read() {
        if (getFile().length() > 0) {
            data.addAll(JSON.parseArray(FileUtil.readFileToString(getFile()), Record.class));
        }
        
    }

    private static void clearInvalidRecords() {
        data.forEach(record -> {
            // 清除时间超过一周的记录
            if (new Date().getTime() - record.getDate().getTime() > 7 * 24 * 60 * 60 * 1000) {
                data.remove(record);
            }
        });
    }

    private static void save() {
        FileUtil.writeStringToFile(getFile(), JSON.toJSONString(new ArrayList<>(data)));
    }

    private static File getFile() {
        File file = FileUtil.getFile("statistics.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }
}