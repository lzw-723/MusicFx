package lzw.musicol.core;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Musicol {
    private static final Musicol musicol = new Musicol();
    private MediaPlayer mediaPlayer;
    private SimpleDoubleProperty volume = new SimpleDoubleProperty(0.3d);

    public double getCurrentProgress() {
        return currentProgress.get();
    }

    public SimpleDoubleProperty currentProgressProperty() {
        return currentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
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

    private Musicol() {
        if (musicol != null) throw new RuntimeException("单例异常");
    }

    public static Musicol get() {
        return musicol;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void getNewPlayer(String url) {
        mediaPlayer = new MediaPlayer(new Media(url));
        mediaPlayer.volumeProperty().bind(volume);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            setCurrentProgress(newValue.toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
        });
        if (handler != null) {

            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    handler.onStart();
                }
            });
            mediaPlayer.setOnPaused(new Runnable() {
                @Override
                public void run() {
                    handler.onPause();
                }
            });
        }
    }

    public void play(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        getNewPlayer(url);
        mediaPlayer.play();
    }

    public boolean isPlaying() {
        System.out.println(mediaPlayer.getStatus().name());
        if (mediaPlayer == null) return false;
        return  mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
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
        if (mediaPlayer == null) return 0;
        return (int) mediaPlayer.getCurrentTime().toSeconds();
    }

    public int getTotalTime() {
        if (mediaPlayer == null) {
            return 1;
        }
        return (int) mediaPlayer.getTotalDuration().toSeconds();
    }

    public boolean playOrPause() {
        if (mediaPlayer == null) return false;
        if (isPlaying()) mediaPlayer.pause();
        else mediaPlayer.play();
        return isPlaying();
    }

    public interface Handler {
        void onStart();

        void onPause();
    }
}
