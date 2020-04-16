/*
 * @Author: lzw-723
 * @Date: 2020-01-27 17:35:16
 * @LastEditTime: 2020-04-16 15:02:49
 * @LastEditors: lzw-723
 * @Description: 启动类（Java-8）
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\MainApp.java
 */
package io.github.lzw;

import com.jfoenix.effects.JFXDepthManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("MusicFx启动，Java版本{}，Javafx版本{}", SystemInfo.javaVersion(), SystemInfo.javafxVersion());
        StackPane root = FXMLLoader.load(getClass().getResource("/fxml/Launch.fxml"));
        double width = 600;
        double height = 500;
        Scene scene = new Scene(root, width, height);
        scene.setFill(null);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        // Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
        
        //     @Override
        //     public void uncaughtException(Thread t, Throwable e) {
        //         logger.error("{}线程, {}异常未捕捉", t.getName(), e.getMessage());
        //     }
        // });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
