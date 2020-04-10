/*
 * @Author: lzw-723
 * @Date: 2020-04-05 11:04:02
 * @LastEditTime: 2020-04-10 10:53:24
 * @LastEditors: lzw-723
 * @Description: 设置面板
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\controller\SettingController.java
 */
package io.github.lzw.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.svg.SVGGlyph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.Config;
import io.github.lzw.MainApp;
import io.github.lzw.bean.Song;
import io.github.lzw.service.SongOService.Type;
import io.github.lzw.util.SongUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

public class SettingController implements Initializable, ControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

    @FXML
    private StackPane root;
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
    @FXML
    private ToggleButton hotkey;
    private JFXDialog dialog;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SVGGlyph dirChooserGlyph = new SVGGlyph(
                "M20 6h-8l-2-2H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm0 12H4V8h16v10z");
        dirChooserGlyph.setSize(12);
        dirChooser.setGraphic(dirChooserGlyph);
        dirChooser.setOnMouseClicked(event -> {
            File dir_music = new DirectoryChooser().showDialog(dirChooser.getScene().getWindow());
            if (dir_music == null || !dir_music.exists()) {
                logger.warn("未选择本地歌曲目录");
                return;
            }
            dir.setText(dir_music.toPath().toString());
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
                Config.getInstance().setType(Type.stringOf(((RadioButton) arg2).getText()));
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
        try {
            dialog = FXMLLoader.load(MainApp.class.getResource("/fxml/dialog/LogDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hotkey.setSelected(Config.getInstance().getHotkeyAble());
        Config.getInstance().hotkeyAbleProperty().bind(hotkey.selectedProperty());
        // log.setOnAction(event -> {
        //     ((Label)dialog.lookup("#text")).setText(LogUtil.getLog());
        //     ((Button)dialog.lookup("#accept")).setOnAction(event2 -> dialog.close());
        //     dialog.show(root);
        // });
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "设置";
    }

    @Override
    public ControllerImpl getController() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public void play(Song song) {

    }

}