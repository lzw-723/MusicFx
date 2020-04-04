package io.github.lzw.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class SongInfoUtil {
    public static AudioFile getAudioFile(File file) {
        try {
            System.out.println(file.exists());
            AudioFile audioFile = AudioFileIO.read(file);
            return audioFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTitle(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        String title = "";
        if (tag != null) {
            title = tag.getFirst(FieldKey.TITLE);
        }
        return "".equals(title) || title == null ? "Unknown" : title;
    }

    public static String getArtist(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        String artist = "";
        if (tag != null) {
            artist = tag.getFirst(FieldKey.ARTIST);
        }
        return "".equals(artist) || artist == null ? "Unknown" : artist;
    }

    public static String getAlbum(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        String album = "";
        if (tag != null) {
            album = tag.getFirst(FieldKey.ALBUM);
        }
        return "".equals(album) || album == null ? "Unknown" : album;
    }

    public static int getLength(File file) {
        try {
            return getAudioFile(file).getAudioHeader().getTrackLength();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static String getArtWork(File file){
        File pic = new File(".", "pic/" + getAlbum(file) + ".jpg");
        if (!pic.exists()) {
            AudioFile audioFile = getAudioFile(file);
            Tag tag = audioFile.getTag();
            byte[] artWork = null;
            if (tag != null && tag.getFirstArtwork() != null) {
                artWork = tag.getFirstArtwork().getBinaryData();
                try {
                    FileUtils.writeByteArrayToFile(pic, artWork);
                    return pic.toURI().toURL().toString();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            try {
                return pic.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "";
    }
}
