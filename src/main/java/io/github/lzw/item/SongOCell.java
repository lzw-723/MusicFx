package io.github.lzw.item;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.MainApp;
import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongL;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class SongOCell extends ListCell<Song> {
    private Handler handler;
    private long time = 0;

    @Override
    public void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            try {
                prefWidthProperty().bind(getListView().widthProperty());
                HBox hBox = FXMLLoader.load(MainApp.class.getResource("/fxml/item/ItemO.fxml"));
                hBox.prefWidthProperty().bind(widthProperty());
                Button play = (Button) hBox.lookup("#play");
                SVGGlyph playGlyph = new SVGGlyph("M8 5v14l11-7z");
                playGlyph.setSize(16);
                play.setGraphic(playGlyph);
                play.setOnAction(event -> {
                    if (handler != null)
                        handler.play(item);
                });
                selectedProperty().addListener((ChangeListener<Boolean>) (arg1, arg2, arg3) -> {
                    play.setVisible(arg3);
                });

                Label title = (Label) hBox.lookup("#title");
                title.setText(item.getTitle());

                HBox left = (HBox) hBox.lookup("#left");
                left.prefWidthProperty().bind(hBox.widthProperty().divide(2));

                Label artist = (Label) hBox.lookup("#artist");
                artist.setText(item.getArtist());

                HBox right = (HBox) hBox.lookup("#right");
                right.prefWidthProperty().bind(hBox.widthProperty().divide(2));

                if (item instanceof SongL) {
                    Label album = (Label) hBox.lookup("#album");
                    album.setText(item.getAlbum());
                    Label time = (Label) hBox.lookup("#time");
                    time.setText(String.valueOf(item.getLength()));
                }

                ObservableList<String> stylesheets = hBox.getStylesheets();
                URL cssUrl = getClass().getResource("/styles/itemo.css");
                if (cssUrl != null) {
                    stylesheets.addAll(cssUrl.toExternalForm());
                }
                hBox.setOnMouseClicked(event -> {
                    if (handler == null) {
                        return;
                    }
                    if (System.currentTimeMillis() - time < 500) {
                        handler.play(item);
                    } else {
                        time = System.currentTimeMillis();
                    }
                });
                setGraphic(hBox);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            setGraphic(new HBox());
        }
    }

    public static interface Handler {
        void play(Song song);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}