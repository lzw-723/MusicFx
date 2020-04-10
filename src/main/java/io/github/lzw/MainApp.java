/*
 * @Author: lzw-723
 * @Date: 2020-01-27 17:35:16
 * @LastEditTime: 2020-04-10 15:02:21
 * @LastEditors: lzw-723
 * @Description: 启动类（Java-8）
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\MainApp.java
 */
package io.github.lzw;

import java.net.URL;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.util.HotkeyUtil;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("MusicFx启动，Java版本{}，Javafx版本{}", SystemInfo.javaVersion(), SystemInfo.javafxVersion());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(
                "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 14.5c-2.49 0-4.5-2.01-4.5-4.5S9.51 7.5 12 7.5s4.5 2.01 4.5 4.5-2.01 4.5-4.5 4.5zm0-5.5c-.55 0-1 .45-1 1s.45 1 1 1 1-.45 1-1-.45-1-1-1z"));
        double width = 800;
        double height = 600;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() > bounds.getHeight() ? bounds.getWidth() : bounds.getHeight();
            width = bounds.getWidth() * 0.618d;
            height = width * 0.618d;
        } catch (Exception e) {
        }
        Image icon = new Image(getClass().getResource("/img/icon.png").toURI().toURL().toString());
        stage.getIcons().add(icon);
        ImageView imageView = new ImageView(icon);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        decorator.setGraphic(imageView);
        Scene scene = new Scene(decorator, width, height);
        HotkeyUtil.registerHotkey(scene, stage);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        URL cssUrl = getClass().getResource("/styles/main.css");
        if (cssUrl != null) {
            stylesheets.addAll(cssUrl.toExternalForm());
        }
        stage.setTitle("MusicFX");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
