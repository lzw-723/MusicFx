package io.github.lzw.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.Config;
import io.github.lzw.bean.Song;
import io.github.lzw.util.SongUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

public class SettingController implements Initializable, ControllerImp {

    @FXML
    private Button dirChooser;
    @FXML
    private JFXTextField dir;
    @FXML
    private JFXSpinner spinner;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SVGGlyph dirChooserGlyph = new SVGGlyph(
                "M20 6h-8l-2-2H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm0 12H4V8h16v10z");
        dirChooserGlyph.setSize(12);
        dirChooser.setGraphic(dirChooserGlyph);
        dirChooser.setOnMouseClicked(event -> {
            dir.setText(new DirectoryChooser().showDialog(dirChooser.getScene().getWindow()).getPath());
            spinner.setVisible(true);
            new Thread(() -> {
                SongUtil.getSongsIgnoreCache();
                Platform.runLater(() -> spinner.setVisible(false));
            }).start();
        });
        this.dir.setText(Config.getInstance().getDir());
        Config.getInstance().dirProperty().bind(this.dir.textProperty());
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "设置";
    }

    @Override
    public ControllerImp getController() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public void play(Song song) {

    }

}