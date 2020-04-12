/*
 * @Author: lzw-723
 * @Date: 2020-04-12 15:52:55
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-12 15:56:39
 * @Description: 艺术家
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\bean\Artist.java
 */
package io.github.lzw.bean;

public class Artist {
    // 艺术家名称
    private String name;
    // 艺术家图片
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

    public Artist(String name, String pic) {
        this.name = name;
        this.pic = pic;
    }
}
