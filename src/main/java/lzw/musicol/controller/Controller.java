package lzw.musicol.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lzw.musicol.core.MusicApi;
import lzw.musicol.core.Musicol;
import lzw.musicol.core.Setting;
import lzw.musicol.core.Song;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

public class Controller implements Initializable {
    @FXML
    private ChoiceBox<String> main_ChoiceBox;
    @FXML
    private TextField main_TextField;
    @FXML
    private Button main_Button;
    @FXML
    private ListView<Song> main_ListView;
    @FXML
    private ImageView main_ImageView;
    @FXML
    private Label main_Lable_songinfo;
    @FXML
    private Slider main_Slider;
    @FXML
    private Button main_Button_playOrPause;
    @FXML
    private Slider main_Slider_volume;

    private Timer timer = new Timer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                main_Slider.setValue(100d * Musicol.get().getCurrentTime() / Musicol.get().getTotalTime());
            }
        }, 1000, 1000);

        main_ChoiceBox.setItems(FXCollections.observableArrayList("QQ", "百度", "酷狗", "网易云", "酷我", "5sing原创"));
        main_ChoiceBox.getSelectionModel().select(3);
        Musicol.get().setHandler(new Musicol.Handler() {
            @Override
            public void onStart() {
                flashPlayButton();
            }

            @Override
            public void onPause() {
                flashPlayButton();
            }
        });
        main_Button.setOnAction(event -> {
            main_ListView.setItems(FXCollections.observableList(MusicApi.getSongs(main_TextField.getText(), main_ChoiceBox.getSelectionModel().getSelectedIndex())));
        });
        main_ListView.setCellFactory((ListView<Song> l) -> {
            return new ColorRectCell();
        });
        main_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                Musicol.get().play(newValue.getUrl());
                main_ImageView.setImage(new Image(newValue.getImgUrl()));
                main_Lable_songinfo.setText(newValue.getName());
            }
        });
        main_Button_playOrPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                        Musicol.get().playOrPause();
            }
        });
        main_Slider_volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Musicol.get().setVolume(newValue.doubleValue());
            }
        });
    }
    private void flashPlayButton(){
        if (Musicol.get().isPlaying()) {
            main_Button_playOrPause.setText("暂停");
            System.out.println("正在播放");
        }
        else{
            main_Button_playOrPause.setText("播放");
            System.out.println("正在暂停");
            }
        System.out.println(Musicol.get().isPlaying());
    }
    public void changeApi(ActionEvent event){
        Preferences preferences = Preferences.userRoot();
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("接口设置");
        textInputDialog.setContentText("请输入maicong-music的url");
        textInputDialog.getEditor().setPromptText("包括http://或https://");
        textInputDialog.getEditor().setText(Setting.getApi());
        textInputDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
               Setting.setApi(textInputDialog.getEditor().getText());
            }
        });
        textInputDialog.show();
    }
    public void onClose() {
        timer.cancel();
    }

    static class ColorRectCell extends ListCell<Song> {
        @Override
        public void updateItem(Song item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                Label label = new Label(item.getName() + " - " + item.getArtist());
                setGraphic(label);
            }
        }
    }
}
