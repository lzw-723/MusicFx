package io.github.lzw.core;

import java.util.ArrayList;
import java.util.List;

import io.github.lzw.bean.Song;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicFx {
    private static final MusicFx musicfx = new MusicFx();
    private MediaPlayer mediaPlayer;
    private SimpleDoubleProperty volume = new SimpleDoubleProperty(0.3d);
    private int index = 0;
    private Song currentSong;
    private List<Song> list = new ArrayList<>();

    public void addList(Song song) {
        list.add(song);
    }

    public void addList(List<Song> songs) {
        list.addAll(songs);
    }

    public void setList(List<Song> songs) {
        list.clear();
        list.addAll(songs);
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

    private SimpleDoubleProperty currentProgress = new SimpleDoubleProperty(0);

    public double getVolume() {
        return volume.get();
    }

    public SimpleDoubleProperty volumeProperty() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume.set(volume);
    }

    private Handler handler;

    private MusicFx() {
        if (musicfx != null)
            throw new RuntimeException("单例异常");
    }

    public static MusicFx get() {
        return musicfx;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void getNewPlayer(Song song) {
        mediaPlayer = new MediaPlayer(new Media(song.getUri()));
        mediaPlayer.volumeProperty().bind(volume);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            setCurrentProgress(newValue.toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
        });
        if (handler != null) {
            mediaPlayer.setOnReady(() -> handler.onReady(song));
            mediaPlayer.setOnPlaying(() -> handler.onStart());
            mediaPlayer.setOnPaused(() -> handler.onPause());
            mediaPlayer.setOnEndOfMedia(() -> {
                handler.OnEnd();
                next();
            });
        }
    }

    private void play(Song song) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        getNewPlayer(song);
        mediaPlayer.play();
        currentSong = song;
    }

    public void play(int index) {
        this.index = index;
        play(list.get(index));
    }

    public void previous() {
        play(--index);
    }

    public void next() {
        play(++index);
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

        void onReady(Song song);

        void onStart();

        void onPause();

        void OnEnd();
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public int getIndex() {
        return index;
    }

}
