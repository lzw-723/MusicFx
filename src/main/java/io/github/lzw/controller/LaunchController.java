/*
 * @Author: lzw-723
 * @Date: 2020-04-13 15:51:34
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-14 11:15:00
 * @Description: 描述信息
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\controller\LaunchController.java
 */
package io.github.lzw.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.Config;
import io.github.lzw.MainApp;
import io.github.lzw.util.ArtistUtil;
import io.github.lzw.util.HotkeyUtil;
import io.github.lzw.util.SongUtil;
import io.reactivex.Observable;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LaunchController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(LaunchController.class);

    @FXML
    private StackPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(() -> {
            if (!usable()) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("请设置本地音乐目录");
                    alert.setOnCloseRequest(event -> {
                        File dir = new DirectoryChooser().showDialog(root.getScene().getWindow());
                        if (dir != null && dir.exists()) {
                            Config.getInstance().setDir(dir.getAbsolutePath());
                            initData();
                            runMainInJavaFxThread();
                        } else {
                            root.getScene().getWindow().hide();
                        }
                    });
                    alert.show();
                });

            } else {
                initData();
                runMainInJavaFxThread();
            }

        }).start();
    }

    private void initData() {
        SongUtil.getSongs();
        ArtistUtil.getArtists().forEach(artist -> ArtistUtil.getSongs(artist));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean usable() {
        return new File(Config.getInstance().getDir()).exists();
    }

    private void runMainInJavaFxThread() {
        Platform.runLater(() -> {
            try {
                runMain();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void runMain() throws Exception {

        root.getScene().getWindow().hide();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/Main.fxml"));

        stage.initModality(Modality.WINDOW_MODAL);

        JFXDecorator decorator = new JFXDecorator(stage, root, true, true, true);
        decorator.setCustomMaximize(false);
        decorator.setGraphic(new SVGGlyph(
                "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 14.5c-2.49 0-4.5-2.01-4.5-4.5S9.51 7.5 12 7.5s4.5 2.01 4.5 4.5-2.01 4.5-4.5 4.5zm0-5.5c-.55 0-1 .45-1 1s.45 1 1 1 1-.45 1-1-.45-1-1-1z"));
        double width = 800;
        double height = 600;

        Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
        width = bounds.getWidth() > bounds.getHeight() ? bounds.getWidth() : bounds.getHeight();
        width = bounds.getWidth() * 0.618d;
        height = width * 0.618d;

        Image icon = new Image(MainApp.class.getResource("/img/icon.png").toURI().toURL().toString());

        stage.getIcons().add(icon);
        ImageView imageView = new ImageView(icon);
        imageView.setOnMouseClicked(event -> {
            logger.info("msg");
        });
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        decorator.setGraphic(imageView);
        Scene scene = new Scene(decorator, width, height);
        // HotkeyUtil.registerHotkey(scene, stage);
        scene.getStylesheets().addAll(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setResizable(true);
        stage.setTitle("MusicFX");
        stage.setScene(scene);
        stage.show();
    }
}