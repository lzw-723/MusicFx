package io.github.lzw.bean;

import java.io.Serializable;

public class Song implements Serializable {
    private String uri;
    private String title;
    private String artist;
    private String album;
    // 时长 秒
    private int length;
    // 图片的URL
    private String artwork;

    // public Song(SongS songS) {
    // isLocal = true;
    // uri = songS.getUri();
    // title = songS.getTitle();
    // artist = songS.getArtist();
    // album = songS.getAlbum();
    // length = songS.getLength();
    // artwork = songS.getArtwork();
    // }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    // public Song(SongO songO) {
    // isLocal = false;
    // uri = songO.getUrl();
    // pic = songO.getPic();
    // title = songO.getTitle();
    // artist = songO.getAuthor();
    // album = "unknown";
    // length = 300;
    // }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTitle()).append("@").append(getArtist()).append("@").append(getAlbum()).append("@")
                .append(getLength()).append("@").append(getUri());
        return stringBuilder.toString();
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

}
