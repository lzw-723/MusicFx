/*
 * @Author: lzw-723
 * @Date: 2020-04-05 11:03:40
 * @LastEditTime: 2020-04-19 09:16:30
 * @LastEditors: lzw-723
 * @Description: 在线音乐搜索面板
 * @FilePath: \MusicFXSingleton\src\main\java\io\github\lzw\controller\OnlineController.java
 */
package io.github.lzw.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.Config;
import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongO;
import io.github.lzw.core.MusicFXSingleton;
import io.github.lzw.item.SongOCell;
import io.github.lzw.service.SongOService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class OnlineController implements Initializable, ControllerImpl {

    @FXML
    private TextField input;
    @FXML
    private Button search;
    @FXML
    private JFXSpinner searching;
    @FXML
    private JFXListView<Song> list;
    private SimpleBooleanProperty searchingProperty = new SimpleBooleanProperty(false);
    private ObservableList<Song> songs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SVGGlyph searchGlyph = new SVGGlyph(
                "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        searchGlyph.setSize(12);
        search.setGraphic(searchGlyph);
        list.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>() {

            @Override
            public ListCell<Song> call(ListView<Song> listview) {
                SongOCell cell = new SongOCell();
                cell.setHandler(song -> {
                    MusicFXSingleton.get().setList(songs);
                    MusicFXSingleton.get().playInList(song);
                });
                return cell;
            }
        });
        list.setItems(songs);
        // songs.addListener((ListChangeListener<SongO>) change -> list.getItems().addAll(change.getList()));
        input.editableProperty().bind(searchingProperty.not());
        search.visibleProperty().bind(searchingProperty.not());
        searching.visibleProperty().bind(searchingProperty);
        search.setOnAction(event -> {
            if (searchingProperty.get()) {
                return;
            }
            searchingProperty.set(true);
            SongOService.getSongOs(input.getText(), Config.getInstance().getType(),
                new SongOService.CallBack() {

                    @Override
                    public void onSuccess(List<SongO> songs) {
                        // list.getItems().clear();
                        OnlineController.this.songs.clear();
                        OnlineController.this.songs.addAll(songs);
                        searchingProperty.set(false);
                    }

                    @Override
                    public void onError(IOException e) {
                        searchingProperty.set(false);
                    }
                });
            });
    }

    @Override
    public String getTitle() {
        return "在线搜索";
    }

    @Override
    public ControllerImpl getController() {
        return this;
    }

    @Override
    public void onPlay(Song song) {
        if (song instanceof SongO) {
            list.getSelectionModel().select(song);
        }
    }

}