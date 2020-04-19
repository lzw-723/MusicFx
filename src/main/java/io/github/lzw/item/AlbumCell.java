/*
 * @Author: lzw-723
 * @Date: 2020-04-15 20:52:19
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-19 09:16:10
 * @Description: 描述信息
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\item\AlbumCell.java
 */
package io.github.lzw.item;

import static javafx.animation.Interpolator.EASE_BOTH;

import java.io.IOException;

import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.MainApp;
import io.github.lzw.bean.Album;
import io.github.lzw.core.MusicFx;
import io.github.lzw.util.AlbumUtil;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AlbumCell extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(AlbumCell.class);

    public AlbumCell(Album album) {
        
        try {
            StackPane content = FXMLLoader.load(MainApp.class.getResource("/fxml/item/ItemAlbum.fxml"));
            VBox.setVgrow(content, Priority.ALWAYS);
            init(album, content);
            getChildren().add(content);

        } catch (IOException e) {
            logger.error("{}布局加载失败，{}", album.getName(), e.getMessage());
            e.printStackTrace();
        }
    }

    private void init(Album album, StackPane content) {
        StackPane header = (StackPane) content.lookup("#header");
        header.setStyle("-fx-background-color: " + getRandomColor());

        Label artistName = (Label) content.lookup("#album");
        artistName.setText(album.getName());

        // Label count = (Label) content.lookup("#album");
        // count.setText(String.valueOf(AlbumUtil.getSongs(album).size()));

        // ImageView artwork = (ImageView) content.lookup("#artwork");
        // try {
        //     artwork.setImage(new Image(album.getPic()));
        // } catch (Exception e) {
        //     logger.warn("专辑{}图片加载失败", album.getName());
        // }

        StackPane body = (StackPane) content.lookup("#body");
        body.setStyle("-fx-background-color: " + getRandomColor());

        Button button = (Button) content.lookup("#button");
        button.setScaleX(0);
        button.setScaleY(0);
        SVGGlyph glyph = new SVGGlyph(-1, "test",
                "M1008 6.286q18.857 13.714 15.429 36.571l-146.286 877.714q-2.857 16.571-18.286 25.714-8 4.571-17.714 4.571-6.286 "
                        + "0-13.714-2.857l-258.857-105.714-138.286 168.571q-10.286 13.143-28 13.143-7.429 "
                        + "0-12.571-2.286-10.857-4-17.429-13.429t-6.571-20.857v-199.429l493.714-605.143-610.857 "
                        + "528.571-225.714-92.571q-21.143-8-22.857-31.429-1.143-22.857 18.286-33.714l950.857-548.571q8.571-5.143 18.286-5.143"
                        + " 11.429 0 20.571 6.286z",
                Color.WHITE);
        glyph.setSize(20, 20);
        button.setGraphic(glyph);

        button.setStyle("-fx-background-radius: 50%; -fx-background-color: " + getRandomColor());

        button.setOnAction(event -> MusicFx.get().setList(AlbumUtil.getSongs(album)));

        button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
            return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
        }, header.boundsInParentProperty(), button.heightProperty()));
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(240), new KeyValue(button.scaleXProperty(), 1, EASE_BOTH),
                        new KeyValue(button.scaleYProperty(), 1, EASE_BOTH)));
        animation.setDelay(Duration.millis(100 * 3 + 1000));
        animation.play();
        JFXDepthManager.setDepth(content, 1);
        header.prefHeight(header.getWidth() * 0.618);
        content.setStyle("-fx-background-radius: 5 5 5 5");
    }

    private String getRandomColor() {
        int i = (int) (Math.random() * 12);
        String color = "#FFFFFF";
        switch (i) {
            case 0:
                color = "#8F3F7E";
                break;
            case 1:
                color = "#B5305F";
                break;
            case 2:
                color = "#CE584A";
                break;
            case 3:
                color = "#DB8D5C";
                break;
            case 4:
                color = "#DA854E";
                break;
            case 5:
                color = "#E9AB44";
                break;
            case 6:
                color = "#FEE435";
                break;
            case 7:
                color = "#99C286";
                break;
            case 8:
                color = "#01A05E";
                break;
            case 9:
                color = "#4A8895";
                break;
            case 10:
                color = "#16669B";
                break;
            case 11:
                color = "#2F65A5";
                break;
            case 12:
                color = "#4E6A9C";
                break;
            default:
                break;
        }
        return color;
    }

}