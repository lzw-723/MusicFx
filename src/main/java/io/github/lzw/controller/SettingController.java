package io.github.lzw.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import java.awt.Desktop;
import java.io.IOException;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.Config;
import io.github.lzw.bean.Song;
import io.github.lzw.util.SongUtil;
import io.github.lzw.util.SongUtilO;
import io.github.lzw.util.SongUtilO.Type;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.stage.DirectoryChooser;

public class SettingController implements Initializable, ControllerImp {

    @FXML
    private Button dirChooser;
    @FXML
    private JFXTextField dir;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private RadioButton netease;
    @FXML
    private RadioButton qq;
    @FXML
    private RadioButton baidu;
    @FXML
    private RadioButton kuwo;
    @FXML
    private RadioButton kugou;
    @FXML
    private Hyperlink link;

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
        netease.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
                Config.getInstance().setType(Type.stringOf(((RadioButton)arg2).getText()));
            }
        });
        switch (Config.getInstance().getType()) {
            case Netease:
                netease.setSelected(true);
                break;
                case QQ:
                qq.setSelected(true);
                break;
                case Kuwo:
                kuwo.setSelected(true);
                break;
                case Baidu:
                baidu.setSelected(true);
                break;
                case Kugou:
                kugou.setSelected(true);
                break;
            default:
                break;
        }
        link.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI(link.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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