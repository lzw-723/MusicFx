package io.github.lzw.bean;

import java.io.Serializable;

public class SongS implements Serializable {
    private String uri;
    private String title;
    private String artist;
    private String album;
    private long length;

    public SongS(Song song) {
        uri = song.getUri();
        title = song.getTitle();
        artist = song.getArtist();
        album = song.getAlbum();
        length = song.getLength();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
