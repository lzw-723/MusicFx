/*
 * @Author: lzw-723
 * @Date: 2020-02-01 14:55:10
 * @LastEditTime: 2020-08-01 21:24:07
 * @LastEditors: lzw-723
 * @Description: 获取本地音频信息的工具类
 */
package io.github.lzw.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.slf4j.LoggerFactory;

public class SongInfoUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SongInfoUtil.class);

    public static boolean checkAvailable(File file) {
        return file.exists() && getLength(file) > 0;
    }

    public static AudioFile getAudioFile(File file) {
        try {
            // 关闭jaudiotagger日志输出
            Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
            AudioFile audioFile = AudioFileIO.read(file);
            return audioFile;
        } catch (Exception e) {
            logger.warn("获取音频文件{}失败，{}", file.getName(), e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String getTitle(File file) {
        Tag tag = getAudioFile(file).getTag();
        String title = "";
        if (tag != null) {
            title = tag.getFirst(FieldKey.TITLE);
        }
        return "".equals(title) || title == null ? "Unknown" : title;
    }

    public static String getArtist(File file) {
        Tag tag = getAudioFile(file).getTag();
        String artist = "";
        if (tag != null) {
            artist = tag.getFirst(FieldKey.ARTIST);
        }
        return "".equals(artist) || artist == null ? "Unknown" : artist;
    }

    public static String getAlbum(File file) {
        Tag tag = getAudioFile(file).getTag();
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
            logger.error("音频{}长度获取失败，{}", file.getName(), e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }

    public static String getArtWork(File file) {
        File pic = FileUtil.getFileOnAppDir("pic/" + getAlbum(file).hashCode() + ".jpg");
        if (!pic.exists()) {
            Tag tag = getAudioFile(file).getTag();
            byte[] artWork = null;
            if (tag != null && tag.getFirstArtwork() != null) {
                artWork = tag.getFirstArtwork().getBinaryData();
                try {
                    FileUtils.writeByteArrayToFile(pic, artWork);
                    return pic.toURI().toURL().toString();
                } catch (IOException e) {
                    logger.error("专辑图片{}缓存失败，{}", file.getName(), e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            try {
                return pic.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                logger.error("专辑图片缓存{}返回失败，{}", pic.getName(), e.getMessage());
                e.printStackTrace();
            }
        }

        return "";
    }

    public static String getArtistCover(File file) {
        if (Integer.valueOf(0).intValue() == 0) {
            return "";
        }
        File cover_art = FileUtil.getFile("artist/" + getArtist(file).hashCode() + ".jpg");
        if (!cover_art.exists()) {
            Tag tag = getAudioFile(file).getTag();
            if (tag != null) {
                try {
                    FileUtils.writeByteArrayToFile(cover_art, tag.getFields(FieldKey.COVER_ART).get(0).getRawContent());
                    return cover_art.toURI().toURL().toString();
                } catch (KeyNotFoundException | IOException e) {
                    logger.error("艺术家图片{}缓存失败，{}", cover_art.getName(), e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            try {
                return cover_art.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                logger.error("专辑图片缓存{}返回失败，{}", cover_art.getName(), e.getMessage());
                e.printStackTrace();
            }
        }
        return "";
    }
}
