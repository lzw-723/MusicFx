package io.github.lzw.item;

import io.github.lzw.bean.Song;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class SongOCell extends ListCell<Song> {
        @Override
        public void updateItem(Song item, boolean empty) {
            super.updateItem(item, empty);
            Label label = new Label();
            if (item != null) {
                label.setText(item.getTitle());
                setGraphic(label);
            }
        }
    }