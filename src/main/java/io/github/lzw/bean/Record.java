/*
 * @Author: lzw-723
 * @Date: 2020-04-19 08:45:14
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-19 08:46:22
 * @Description: 记录
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\bean\Record.java
 */
package io.github.lzw.bean;

import java.util.Date;

public class Record {
    private Date date;
    private SongL song;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SongL getSong() {
        return song;
    }

    public void setSong(SongL song) {
        this.song = song;
    }

    public Record(Date date, SongL song) {
        this.date = date;
        this.song = song;
    }
}