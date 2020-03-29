/*
 * @Author: lzw-723 
 * @Date: 2020-03-23 15:11:13 
 * @Last Modified by: lzw-723
 * @Last Modified time: 2020-03-23 15:17:20
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