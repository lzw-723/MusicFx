package io.github.lzw.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleNode;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.Config;
import io.github.lzw.MainApp;
import io.github.lzw.bean.Song;
import io.github.lzw.core.MusicFx;
import io.github.lzw.core.MusicFx.Handler;
import io.github.lzw.core.MusicFx.Method;
import io.github.lzw.item.SongList;
import io.github.lzw.util.TimeFormater;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class MainController implements Initializable {

    @FXML
    private JFXToggleNode search;
    @FXML
    private JFXToggleNode local;
    @FXML
    private JFXToggleNode setting;

    @FXML
    private Label title;
    @FXML
    private Pane pane;

    @FXML
    private ImageView imageView;
    @FXML
    private Label title_song;
    @FXML
    private Label artist;
    @FXML
    private Label time;
    @FXML
    private JFXSlider slider1;
    @FXML
    private Button previous;
    @FXML
    private Button play;
    @FXML
    private Button next;
    @FXML
    private Button method;
    @FXML
    private Button play_list;
    @FXML
    private HBox layout;
    @FXML
    private JFXSlider slider2;

    private JFXPopup jfxPopup;
    private Tooltip tooltip = new Tooltip();

    private ControllerImpl controller;

    private void initMain() {
        freshControllBiutton();
        slider2.setValue(Config.getInstance().getVolume());
        MusicFx.get().volumeProperty().bind(slider2.valueProperty());
        play.setOnAction(event -> MusicFx.get().playOrPause());
        previous.setOnAction(event -> MusicFx.get().previous());
        next.setOnAction(event -> MusicFx.get().next());
        method.setOnAction(event -> MusicFx.get().changeMethod());
        Tooltip.install(method, tooltip);
        play_list.setOnAction(event -> {
            jfxPopup = new JFXPopup(new SongList(MusicFx.get().getList()));
            jfxPopup.show(play_list, PopupVPosition.BOTTOM, PopupHPosition.RIGHT);
        });
        Config.getInstance().volumeProperty().bind(MusicFx.get().volumeProperty());
        // slider1.setOnTouchMoved(event -> {
        //     MusicFx.get().seek(slider1.getValue());
        //     slider1.valueProperty().bind(MusicFx.get().currentProgressProperty());
        // });
        slider1.setValueFactory(slider -> Bindings.createStringBinding(
                () -> TimeFormater.format((long) (1000 * slider.getValue() * MusicFx.get().getTotalTime())),
                slider.valueProperty()));
        slider1.valueProperty().bind(MusicFx.get().currentProgressProperty());
        slider1.setOnMousePressed(event -> slider1.valueProperty().unbind());
        slider1.setOnMouseReleased(event -> {
            MusicFx.get().seek(slider1.getValue());
            slider1.valueProperty().bind(MusicFx.get().currentProgressProperty());
        });
        slider2.setValueFactory(slider -> Bindings.createStringBinding(
            () -> String.valueOf(Math.round(slider.getValue() * 100)) + "%",
            slider.valueProperty()));
        MusicFx.get().currentProgressProperty()
                .addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> time
                        .setText(TimeFormater.format(MusicFx.get().getCurrentTime() * 1000) + " : "
                                + TimeFormater.format(MusicFx.get().getTotalTime() * 1000)));

        MusicFx.get().setHandler(new Handler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                freshControllBiutton();
            }

            @Override
            public void onPause() {
                // TODO Auto-generated method stub
                freshControllBiutton();
            }

            @Override
            public void init() {
                // TODO Auto-generated method stub

            }

            @Override
            public void OnEnd() {
                freshControllBiutton();
            }

            @Override
            public void onReady(Song song) {
                controller.play(song);
                title_song.setText(song.getTitle());
                artist.setText(song.getArtist());
                try {
                    new Thread(() -> {
                        Image image = new Image(song.getArtwork());
                        Platform.runLater(() -> imageView.setImage(image));
                    }).start();
                    ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMethodChanged(Method method) {

                SVGGlyph methodGlyph;
                switch (method) {
                    case Loop:
                        methodGlyph = new SVGGlyph("M7 7h10v3l4-4-4-4v3H5v6h2V7zm10 10H7v-3l-4 4 4 4v-3h12v-6h-2v4z");
                        tooltip.setText("列表循环");
                        break;

                    case Repeat:
                        methodGlyph = new SVGGlyph(
                                "M7 7h10v3l4-4-4-4v3H5v6h2V7zm10 10H7v-3l-4 4 4 4v-3h12v-6h-2v4zm-4-2V9h-1l-2 1v1h1.5v4H13z");
                        tooltip.setText("单曲循环");
                        break;

                    case Shuffle:
                        methodGlyph = new SVGGlyph(
                                "M10.59 9.17L5.41 4 4 5.41l5.17 5.17 1.42-1.41zM14.5 4l2.04 2.04L4 18.59 5.41 20 17.96 7.46 20 9.5V4h-5.5zm.33 9.41l-1.41 1.41 3.13 3.13L14.5 20H20v-5.5l-2.04 2.04-3.13-3.13z");
                        tooltip.setText("随机播放");
                        break;

                    default:
                        methodGlyph = new SVGGlyph("M7 7h10v3l4-4-4-4v3H5v6h2V7zm10 10H7v-3l-4 4 4 4v-3h12v-6h-2v4z");
                        tooltip.setText("列表循环");
                        break;
                }
                methodGlyph.setSize(10);
                MainController.this.method.setGraphic(methodGlyph);
            }

            @Override
            public void onError() {
                // TODO Auto-generated method stub
            }

            @Override
            public void OnSetNewList() {
                freshControllBiutton();
                MusicFx.get().play(0);
            }
        });

        SVGGlyph searchSvg = new SVGGlyph(
                "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        searchSvg.setFill(Paint.valueOf("#FFFFFF"));
        searchSvg.setSize(16);
        search.setGraphic(searchSvg);
        SVGGlyph localSvg = new SVGGlyph("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        localSvg.setFill(Paint.valueOf("#FFFFFF"));
        localSvg.setSize(16);
        local.setGraphic(localSvg);
        SVGGlyph settingSvg = new SVGGlyph(
                "M19.14,12.94c0.04-0.3,0.06-0.61,0.06-0.94c0-0.32-0.02-0.64-0.07-0.94l2.03-1.58c0.18-0.14,0.23-0.41,0.12-0.61 l-1.92-3.32c-0.12-0.22-0.37-0.29-0.59-0.22l-2.39,0.96c-0.5-0.38-1.03-0.7-1.62-0.94L14.4,2.81c-0.04-0.24-0.24-0.41-0.48-0.41 h-3.84c-0.24,0-0.43,0.17-0.47,0.41L9.25,5.35C8.66,5.59,8.12,5.92,7.63,6.29L5.24,5.33c-0.22-0.08-0.47,0-0.59,0.22L2.74,8.87 C2.62,9.08,2.66,9.34,2.86,9.48l2.03,1.58C4.84,11.36,4.8,11.69,4.8,12s0.02,0.64,0.07,0.94l-2.03,1.58 c-0.18,0.14-0.23,0.41-0.12,0.61l1.92,3.32c0.12,0.22,0.37,0.29,0.59,0.22l2.39-0.96c0.5,0.38,1.03,0.7,1.62,0.94l0.36,2.54 c0.05,0.24,0.24,0.41,0.48,0.41h3.84c0.24,0,0.44-0.17,0.47-0.41l0.36-2.54c0.59-0.24,1.13-0.56,1.62-0.94l2.39,0.96 c0.22,0.08,0.47,0,0.59-0.22l1.92-3.32c0.12-0.22,0.07-0.47-0.12-0.61L19.14,12.94z M12,15.6c-1.98,0-3.6-1.62-3.6-3.6 s1.62-3.6,3.6-3.6s3.6,1.62,3.6,3.6S13.98,15.6,12,15.6z");
        settingSvg.setFill(Paint.valueOf("#FFFFFF"));
        settingSvg.setSize(16);
        setting.setGraphic(settingSvg);
        search.setOnAction(event -> {
            search.setSelected(true);
            local.setSelected(false);
            setting.setSelected(false);
            if (controller instanceof OnlineController) {
                return;
            }
            loadContent("/fxml/Online.fxml");
        });
        local.setOnAction(event -> {
            search.setSelected(false);
            local.setSelected(true);
            setting.setSelected(false);
            if (controller instanceof LocalController) {
                return;
            }
            loadContent("/fxml/Local.fxml");
        });
        setting.setOnAction(event -> {
            search.setSelected(false);
            local.setSelected(false);
            setting.setSelected(true);
            if (controller instanceof SettingController) {
                return;
            }
            loadContent("/fxml/Setting.fxml");
        });
        loadContent("/fxml/Local.fxml");
    }

    private void loadContent(String fxml) {
        pane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxml));
            Pane content = loader.load();
            content.prefHeightProperty().bind(pane.heightProperty());
            content.prefWidthProperty().bind(pane.widthProperty());
            pane.getChildren().add(content);
            controller = loader.getController();
            title.setText(controller.getTitle());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @description: 设定控制按钮（初始化、刷新状态）
     */    
    private void freshControllBiutton() {
        SVGGlyph playGlyph = MusicFx.get().isPlaying() ? new SVGGlyph("M6 19h4V5H6v14zm8-14v14h4V5h-4z")
                : new SVGGlyph("M8 5v14l11-7z");
        playGlyph.setFill(Paint.valueOf("#FFFFFF"));
        playGlyph.setSize(16);
        play.setGraphic(playGlyph);
        SVGGlyph previousGlyph = new SVGGlyph("M6 6h2v12H6zm3.5 6l8.5 6V6z");
        previousGlyph.setFill(Paint.valueOf("#FFFFFF"));
        previousGlyph.setSize(12);
        previous.setGraphic(previousGlyph);
        SVGGlyph nextGlyph = new SVGGlyph("M6 18l8.5-6L6 6v12zM16 6v12h2V6h-2z");
        nextGlyph.setFill(Paint.valueOf("#FFFFFF"));
        nextGlyph.setSize(12);
        next.setGraphic(nextGlyph);
        play_list.setText(String.valueOf(MusicFx.get().count()));
        SVGGlyph play_list_Glyph = new SVGGlyph("M4 10h12v2H4zm0-4h12v2H4zm0 8h8v2H4zm10 0v6l5-3z");
        play_list_Glyph.setSize(10);
        play_list.setGraphic(play_list_Glyph);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initMain();
    }
}
