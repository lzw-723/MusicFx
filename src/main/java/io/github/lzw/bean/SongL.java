package io.github.lzw.bean;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import io.github.lzw.util.SongInfoUtil;

public class SongL extends Song {

    public SongL(String uri) {
        try {
            File file = new File(new URI(uri));
            setUri(uri);
            setTitle(SongInfoUtil.getTitle(file));
            setArtist(SongInfoUtil.getArtist(file));
            setAlbum(SongInfoUtil.getAlbum(file));
            setLength(SongInfoUtil.getLength(file));
            setArtwork(SongInfoUtil.getArtWork(file));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
