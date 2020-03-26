package io.github.lzw.item;

import io.github.lzw.bean.Song;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 * SongCell
 */
public class SongCell extends TableCell<Song, String> {

    @Override
    protected void updateItem(String arg0, boolean arg1) {
        // TODO Auto-generated method stub
        super.updateItem(arg0, arg1);
        if (arg1) {
            setGraphic(new Button(arg0));
        }
    }
}