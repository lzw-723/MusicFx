package io.github.lzw.bean;

import io.github.lzw.util.SongInfoUtil;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Song implements Serializable {
    private SimpleStringProperty uri;
    private SimpleStringProperty title;
    private SimpleStringProperty artist;
    private SimpleStringProperty album;
    private SimpleLongProperty length;
    private String pic;
    private boolean isLocal;

    public Song(String uri) throws MalformedURLException {
        try {
            File file = new File(new URI(uri));
            this.uri = new SimpleStringProperty(uri);
            title = new SimpleStringProperty(SongInfoUtil.getTitle(file));
            artist = new SimpleStringProperty(SongInfoUtil.getArtist(file));
            album = new SimpleStringProperty(SongInfoUtil.getAlbum(file));
            length = new SimpleLongProperty(SongInfoUtil.getLength(file));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Song(SongS songS) {
        isLocal = true;
        uri = new SimpleStringProperty(songS.getUri());
        title = new SimpleStringProperty(songS.getTitle());
        artist = new SimpleStringProperty(songS.getArtist());
        album = new SimpleStringProperty(songS.getAlbum());
        length = new SimpleLongProperty(songS.getLength());
    }

    

    public String getUri() {
        return uri.get();
    }

    public void setUri(String uri) {
        this.uri.set(uri);
    }

    public SimpleStringProperty uriProperty() {
        return uri;
    }

    public String getAlbum() {
        return album.get();
    }

    public void setAlbum(String album) {
        this.album.set(album);
    }

    public SimpleStringProperty albumProperty() {
        return album;
    }

    public long getLength() {
        return length.get();
    }

    public void setLength(long length) {
        this.length.set(length);
    }

    public SimpleLongProperty lengthProperty() {
        return length;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getArtist() {
        return artist.get();
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public SimpleStringProperty artistProperty() {
        return artist;
    }

    public Song(SongO songO) {
        isLocal = false;
        uri = new SimpleStringProperty(songO.getUrl());
        pic = songO.getPic();
        title = new SimpleStringProperty(songO.getTitle());
        artist = new SimpleStringProperty(songO.getAuthor());
        album = new SimpleStringProperty("unkown");
        length = new SimpleLongProperty(300);
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
