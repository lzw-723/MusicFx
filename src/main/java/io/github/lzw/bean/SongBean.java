/*
 * @Author: lzw-723
 * @Date: 2020-03-23 15:59:01
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-12 15:58:02
 * @Description: 来自网络的歌曲数据
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\bean\SongBean.java
 */
package io.github.lzw.bean;

import java.util.List;

/**
 * SongBean
 */
public class SongBean {

    private List<SongBeanO> data;
    private int code;
    private String error;

    public List<SongBeanO> getData() {
        return data;
    }

    public void setData(List<SongBeanO> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public class SongBeanO {
        private String type;
        private String link;
        private long songid;
        private String title;
        private String author;
        private String lrc;
        private String url;
        private String pic;
    
        public String getType() {
            return type;
        }
    
        public void setType(String type) {
            this.type = type;
        }
    
        public String getLink() {
            return link;
        }
    
        public void setLink(String link) {
            this.link = link;
        }
    
        public long getSongid() {
            return songid;
        }
    
        public void setSongid(long songid) {
            this.songid = songid;
        }
    
        public String getTitle() {
            return title;
        }
    
        public void setTitle(String title) {
            this.title = title;
        }
    
        public String getAuthor() {
            return author;
        }
    
        public void setAuthor(String author) {
            this.author = author;
        }
    
        public String getLrc() {
            return lrc;
        }
    
        public void setLrc(String lrc) {
            this.lrc = lrc;
        }
    
        public String getUrl() {
            return url;
        }
    
        public void setUrl(String url) {
            this.url = url;
        }
    
        public String getPic() {
            return pic;
        }
    
        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}