/*
 * @Author: lzw-723
 * @Date: 2020-03-23 15:09:05
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-12 15:59:06
 * @Description: 网络歌曲
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\bean\SongO.java
 */

package io.github.lzw.bean;

import io.github.lzw.bean.SongBean.SongBeanO;

/**
 * SongO
 */
public class SongO extends Song {

    public SongO(SongBeanO beanO) {
        setUri(beanO.getUrl());
        setTitle(beanO.getTitle());
        setArtist(beanO.getAuthor());
        setArtwork(beanO.getPic());
    }

}