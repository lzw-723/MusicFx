package io.github.lzw.item;

import java.util.List;

import com.jfoenix.controls.JFXListView;

import io.github.lzw.bean.Song;
import io.github.lzw.core.MusicFXSingleton;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * SongListCell
 */
public class SongList extends JFXListView<Song>{

    public SongList(List<Song> songs) {
        super();
        setPrefWidth(300);
        setPrefHeight(300);
        setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){

            @Override
            public ListCell<Song> call(ListView<Song> arg0) {
                SongOCell cell = new SongOCell();
                cell.setHandler(new SongOCell.Handler(){
                
                    @Override
                    public void play(Song song) {
                        MusicFXSingleton.get().playInList(song);
                    }
                });
                return cell;
            }
            
        });
        getItems().addAll(songs);
    }
    
}