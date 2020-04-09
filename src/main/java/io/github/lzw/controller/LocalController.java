/*
 * @Author: lzw-723 
 * @Date: 2020-03-24 09:35:55 
 * @Last Modified by: lzw-723
 * @Last Modified time: 2020-03-24 09:38:46
 */
package io.github.lzw.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongL;
import io.github.lzw.core.MusicFx;
import io.github.lzw.item.SongOCell;
import io.github.lzw.util.SongUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * LocalController
 */
public class LocalController implements Initializable, ControllerImpl {
    @FXML
    private JFXListView<Song> table;
    private List<SongL> songs = new ArrayList<>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        songs.clear();
        table.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>() {

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
        
        new Thread(() -> {
            songs.addAll(SongUtil.getSongs());
            table.getItems().addAll(FXCollections.observableList(songs));
            Song song = MusicFx.get().getCurrentSong();
            if (song != null && song instanceof SongL) {
                play(song);
            }
        }).start();
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "本地音乐";
    }

    @Override
    public ControllerImpl getController() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public void play(Song song) {
        // TODO Auto-generated method stub
        if (song instanceof SongL) {
            table.getSelectionModel().select(song);
        }
    }

}