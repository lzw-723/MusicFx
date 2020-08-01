package io.github.lzw.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.Config;
import io.github.lzw.bean.Song;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicFx {
    private final static Logger LOGGER = LoggerFactory.getLogger(MusicFx.class);
    private static final MusicFx musicfx = new MusicFx();
    private MediaPlayer mediaPlayer;
    private SimpleDoubleProperty volume = new SimpleDoubleProperty(Config.getInstance().getVolume());
    private int index = 0;
    private SimpleDoubleProperty currentProgress = new SimpleDoubleProperty(0);
    private Song currentSong;
    private List<Song> list = new ArrayList<>();
    private Method method = Method.Loop;

    // public synchronized void addList(Song song) {
    //     list.add(song);
    // }

    // public synchronized void addList(List<? extends Song> songs) {
    //     list.addAll(songs);
    // }

    public synchronized void setList(List<? extends Song> songs) {
        if (list.equals(songs)) {
            // System.out.println("重复设置列表");
            return;
        }
        list.clear();
        list.addAll(songs);
        if (handler != null) {
            handler.OnSetNewList();
        }
    }

    public List<Song> getList() {
        return list;
    }

    public int count() {
        return list.size();
    }

    public double getCurrentProgress() {
        return currentProgress.get();
    }

    public SimpleDoubleProperty currentProgressProperty() {
        return currentProgress;
    }

    private void setCurrentProgress(double currentProgress) {
        this.currentProgress.set(currentProgress);
    }

    public double getVolume() {
        return volume.get();
    }

    public SimpleDoubleProperty volumeProperty() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume.set(volume);
    }

    public synchronized void seek(Double progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(getTotalTime() * progress));
        }
    }

    private Handler handler;

    private MusicFx() {
        if (musicfx != null) {
            throw new RuntimeException("单例异常");
        }
    }

    public static MusicFx get() {
        return musicfx;
    }

    public synchronized void setHandler(Handler handler) {
        this.handler = handler;
        this.handler.onMethodChanged(method);
    }

    private synchronized void getNewPlayer(Song song) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        try {
            mediaPlayer = new MediaPlayer(new Media(song.getUri()));
        } catch (MediaException e) {
            LOGGER.error("Media初始化失败", e);
            if (handler != null) {
                handler.onError();
            }
            LOGGER.warn("Media（{}）初始化失败，尝试重试/下一首", song.getUri());
            next();
        }
        mediaPlayer.volumeProperty().bind(volume);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            setCurrentProgress(newValue.toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
        });
        if (handler != null) {
            mediaPlayer.setOnReady(() -> handler.onReady(song));
            mediaPlayer.setOnPlaying(() -> handler.onStart());
            mediaPlayer.setOnPaused(() -> handler.onPause());
            mediaPlayer.setOnError(() -> {
                LOGGER.error("MediaPlayer播放错误", mediaPlayer.getError().getCause());
                handler.onError();
                next();
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                handler.OnEnd();
                next();
            });
        }
    }

    private synchronized void play(Song song) {
        LOGGER.info("播放歌曲{}", song.toString());
        getNewPlayer(song);
        mediaPlayer.play();
        currentSong = song;
    }

    public synchronized void play(int index) {
        if (index < 0) {
            index = list.size() + index;
        }
        index %= list.size();
        this.index = index;
        play(list.get(index));
    }

    public synchronized void playInList(Song song) {
        for (int i = 0; i < list.size(); i++) {
            if (song.getUri().equals(list.get(i).getUri())) {
                play(i);
                return;
            }
        }
    }

    public synchronized void previous() {
        switch (method) {
            case Loop:
                play(--index);
                break;

            case Repeat:
                play(index);
                break;

            case Shuffle:
                play((int) (Math.random() * count()));
                break;

            default:
                break;
        }
    }

    public synchronized void next() {
        switch (method) {
            case Loop:
                play(++index);
                break;

            case Repeat:
                play(index);
                break;

            case Shuffle:
                play((int) (Math.random() * count()));
                break;

            default:
                break;
        }
    }

    public boolean isPlaying() {
        if (mediaPlayer == null)
            return false;
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public int getBufferTime() {
        if (mediaPlayer == null) {
            return 0;
        }
        return (int) mediaPlayer.getBufferProgressTime().toSeconds();
    }

    public int getCurrentTime() {
        if (mediaPlayer == null)
            return 0;
        return (int) mediaPlayer.getCurrentTime().toSeconds();
    }

    public int getTotalTime() {
        if (mediaPlayer == null) {
            return 1;
        }
        return (int) mediaPlayer.getTotalDuration().toSeconds();
    }

    public boolean playOrPause() {
        if (mediaPlayer == null)
            return false;
        if (isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.play();
        return isPlaying();
    }

    public interface Handler {

        void init();

        void onError();

        void onReady(Song song);

        void onStart();

        void onPause();

        void OnEnd();

        void OnSetNewList();

        void onMethodChanged(Method method);
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public int getIndex() {
        return index;
    }

    public enum Method {
        Loop, Repeat, Shuffle;
    }

    public Method getMethod() {
        return method;
    }

    public synchronized void changeMethod() {
        switch (method) {
            case Loop:
                method = Method.Repeat;
                break;
            case Repeat:
                method = Method.Shuffle;
                break;
            case Shuffle:
                method = Method.Loop;
                break;
            default:
                method = Method.Loop;
                break;
        }
        if (handler != null) {
            handler.onMethodChanged(method);
        }
    }

}
