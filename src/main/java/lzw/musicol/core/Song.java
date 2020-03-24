package lzw.musicol.core;

public class Song {
    private String name;
    private String artist;
    private String imgUrl;
    private String url;

    public Song(String name, String artist, String imgUrl, String url) {
        this.name = name;
        this.artist = artist;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public Song(String name, String artist, String url) {
        this.name = name;
        this.artist = artist;
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
