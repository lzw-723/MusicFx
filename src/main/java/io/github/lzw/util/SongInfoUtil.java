package io.github.lzw.util;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import javafx.scene.image.Image;

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
        String title = tag.getFirst(FieldKey.TITLE);
        return "".equals(title) || title == null ? "Unkown" : title;
    }

    public static String getArtist(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        String artist = tag.getFirst(FieldKey.ARTIST);
        return "".equals(artist) || artist == null ? "Unkown" : artist;
    }

    public static String getAlbum(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        String album = tag.getFirst(FieldKey.ALBUM);
        return "".equals(album) || album == null ? "Unkown" : album;
    }

    public static long getLength(File file) {
        try {
            return getAudioFile(file).getAudioHeader().getTrackLength();
        } catch (Exception e) {
            e.printStackTrace();
            return 1l;
        }
    }

    public static Image getArtWork(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        byte[] artWork = tag.getFirstArtwork().getBinaryData();
        Image image = new Image(new ByteArrayInputStream(artWork));
        return image;
    }
}
