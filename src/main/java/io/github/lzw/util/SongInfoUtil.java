package io.github.lzw.util;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v22Tag;
import org.jaudiotagger.tag.id3.ID3v23FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.id3.ID3v24FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import it.sauronsoftware.jave.Encoder;
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
        if (tag instanceof ID3v1Tag) {
            return ((ID3v1Tag) tag).getTitle().toString();
        } else if (tag instanceof ID3v22Tag) {
            return ((ID3v22Tag) tag).getFirst(FieldKey.TITLE);
        } else if (tag instanceof ID3v23Tag) {
            return ((ID3v23Tag) tag).getFirst(ID3v23FieldKey.TITLE);
        } else if (tag instanceof ID3v24Tag) {
            return ((ID3v24Tag) tag).getFirst(ID3v24FieldKey.TITLE);
        }
        return "Unkown";
    }

    public static String getArtist(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        if (tag instanceof ID3v1Tag) {
            return ((ID3v1Tag) tag).getArtist().toString();
        } else if (tag instanceof ID3v22Tag) {
            return ((ID3v22Tag) tag).getFirst(FieldKey.ARTIST);
        } else if (tag instanceof ID3v23Tag) {
            return ((ID3v23Tag) tag).getFirst(ID3v23FieldKey.ARTIST);
        } else if (tag instanceof ID3v24Tag) {
            return ((ID3v24Tag) tag).getFirst(ID3v24FieldKey.ARTIST);
        }
        return "Unkown";
    }

    public static String getAlbum(File file) {
        AudioFile audioFile = getAudioFile(file);
        Tag tag = audioFile.getTag();
        if (tag instanceof ID3v1Tag) {
            return ((ID3v1Tag) tag).getAlbum().toString();
        } else if (tag instanceof ID3v22Tag) {
            return ((ID3v22Tag) tag).getFirst(FieldKey.ALBUM);
        } else if (tag instanceof ID3v23Tag) {
            return ((ID3v23Tag) tag).getFirst(ID3v23FieldKey.ALBUM);
        } else if (tag instanceof ID3v24Tag) {
            return ((ID3v24Tag) tag).getFirst(ID3v24FieldKey.ALBUM);
        }
        return "Unkown";
    }

    public static long getLength(File file) {
        try {
            return new Encoder().getInfo(file).getDuration() / 1000;
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
