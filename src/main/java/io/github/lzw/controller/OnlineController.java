package io.github.lzw.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongO;
import io.github.lzw.core.MusicFx;
import io.github.lzw.item.SongOCell;
import io.github.lzw.util.SongUtilO;
import io.github.lzw.util.SongUtilO.Type;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class OnlineController implements Initializable, ControllerImp {

    @FXML
    private TextField input;
    @FXML
    private Button search;
    @FXML
    private JFXListView<Song> list;
    private List<SongO> songs = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SVGGlyph searchGlyph = new SVGGlyph(
                "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        searchGlyph.setSize(12);
        search.setGraphic(searchGlyph);
        list.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>() {

            @Override
            public ListCell<Song> call(ListView<Song> arg0) {
                SongOCell cell = new SongOCell();
                cell.setHandler(new SongOCell.Handler() {

                    @Override
                    public void play(Song song) {
                        MusicFx.get().setList(songs);
                        MusicFx.get().playInList(song);
                    }
                });
                return cell;
            }
        });
        search.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                list.getItems().clear();
                Type type = Type.Netease;
                songs = SongUtilO.getSongOs(input.getText(), type);
                list.getItems().addAll(songs);
            }
        });
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "在线搜索";
    }

    @Override
    public ControllerImp getController() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public void play(Song song) {
        // TODO Auto-generated method stub
        if (song instanceof SongO) {
            list.getSelectionModel().select(song);
        }
    }

}