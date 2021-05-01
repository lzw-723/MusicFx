/*
 * @Author: lzw-723
 * @Date: 2020-04-14 15:39:13
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-14 15:40:22
 * @Description: 专辑
 * @FilePath: \MusicFXSingleton\src\main\java\io\github\lzw\bean\Album.java
 */
package io.github.lzw.bean;

public class Album {
    private String name;
    private String pic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Album(String name, String pic) {
        this.name = name;
        this.pic = pic;
    }
}