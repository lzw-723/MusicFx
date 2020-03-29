package io.github.lzw;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.bean.Song;
import io.github.lzw.core.MusicFx;
import io.github.lzw.core.MusicFx.Handler;
import io.github.lzw.item.SongOCell;
import io.github.lzw.util.SongUtil;
import io.github.lzw.util.SongUtilO;
import io.github.lzw.util.TimeFormater;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

public class FXMLController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<Song> table;
    @FXML
    private ImageView imageView;
    @FXML
    private Label title;
    @FXML
    private Label artist;
    @FXML
    private Label time;
    @FXML
    private Slider slider1;
    @FXML
    private Button previous;
    @FXML
    private Button play;
    @FXML
    private Button next;
    @FXML
    private Button method;
    @FXML
    private JFXBadge play_list_badge;
    @FXML
    private Button play_list;
    @FXML
    private HBox layout;
    @FXML
    private Slider slider2;
    @FXML
    private TextField input;
    @FXML
    private Button search;
    @FXML
    private ListView<Song> list;
    @FXML
    private Button dirFinder;
    @FXML
    private TextField dir;
    private List<Song> lists = new ArrayList<>();
    private List<Song> songOs = new ArrayList<>();

    private void initMain() {
        freshControllBiutton();
        MusicFx.get().volumeProperty().bind(slider2.valueProperty());
        MusicFx.get().currentProgressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> observable, final Number oldValue,
                    final Number newValue) {
                slider1.setValue(newValue.doubleValue());
                time.setText(TimeFormater.format(MusicFx.get().getCurrentTime() * 1000) + " : "
                        + TimeFormater.format(MusicFx.get().getTotalTime() * 1000));
            }
        });
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

            }

            @Override
            public void onReady(Song song) {
                // TODO Auto-generated method stub
                // table.getSelectionModel().select(song);
                title.setText(song.getTitle());
                artist.setText(song.getArtist());
                try {
                    Image image;
                    image = new Image(song.getArtwork());
                    imageView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 设定控制按钮（初始化、刷新状态）
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
        SVGGlyph methodGlyph = new SVGGlyph("M7 7h10v3l4-4-4-4v3H5v6h2V7zm10 10H7v-3l-4 4 4 4v-3h12v-6h-2v4z");
        methodGlyph.setSize(10);
        method.setGraphic(methodGlyph);
        // play_list_badge.getClip().set
        play_list_badge.setText(String.valueOf(MusicFx.get().count()));
        play_list_badge.setEnabled(true);
        SVGGlyph play_list_Glyph = new SVGGlyph("M4 10h12v2H4zm0-4h12v2H4zm0 8h8v2H4zm10 0v6l5-3z");
        play_list_Glyph.setSize(10);
        play_list.setGraphic(play_list_Glyph);
    }

    private void initLocal() {
        table.getColumns().get(0).prefWidthProperty().bind(table.widthProperty().divide(4));
        ((TableColumn<Song, String>) table.getColumns().get(0))
                .setCellValueFactory(new Callback<CellDataFeatures<Song, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Song, String> arg0) {
                        return new SimpleStringProperty(arg0.getValue().getTitle());
                    }
                });
        ((TableColumn<Song, String>) table.getColumns().get(0)).setCellFactory(arg0 -> new TableCell<Song, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                // TODO Auto-generated method stub
                super.updateItem(item, empty);
                if (!empty) {
                    HBox hBox = new HBox(new Label(item));
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });
        table.getColumns().get(1).prefWidthProperty().bind(table.widthProperty().divide(4));
        ((TableColumn<Song, String>) table.getColumns().get(1))
                .setCellValueFactory(new Callback<CellDataFeatures<Song, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Song, String> arg0) {
                        return new SimpleStringProperty(arg0.getValue().getArtist());
                    }
                });
        ((TableColumn<Song, String>) table.getColumns().get(1)).setCellFactory(arg0 -> new TableCell<Song, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                // TODO Auto-generated method stub
                super.updateItem(item, empty);
                if (!empty) {
                    HBox hBox = new HBox(new Label(item));
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });
        table.getColumns().get(2).prefWidthProperty().bind(table.widthProperty().divide(4));
        ((TableColumn<Song, String>) table.getColumns().get(2))
                .setCellValueFactory(new Callback<CellDataFeatures<Song, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Song, String> arg0) {
                        return new SimpleStringProperty(arg0.getValue().getAlbum());
                    }
                });
        ((TableColumn<Song, String>) table.getColumns().get(2)).setCellFactory(arg0 -> new TableCell<Song, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                // TODO Auto-generated method stub
                super.updateItem(item, empty);
                if (!empty) {
                    HBox hBox = new HBox(new Label(item));
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });
        table.getColumns().get(3).prefWidthProperty().bind(table.widthProperty().divide(4));
        ((TableColumn<Song, Number>) table.getColumns().get(3))
                .setCellValueFactory(new Callback<CellDataFeatures<Song, Number>, ObservableValue<Number>>() {
                    @Override
                    public ObservableValue<Number> call(CellDataFeatures<Song, Number> arg0) {
                        return new SimpleIntegerProperty(arg0.getValue().getLength());
                    }
                });
        ((TableColumn<Song, Number>) table.getColumns().get(3)).setCellFactory(arg0 -> new TableCell<Song, Number>() {
            @Override
            protected void updateItem(Number arg0, boolean arg1) {
                // TODO Auto-generated method stub
                super.updateItem(arg0, arg1);
                if (!arg1) {
                    HBox hBox = new HBox(new Label(TimeFormater.format(arg0.intValue() * 1000)));
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                }
            }
        });
        table.getItems().addAll(FXCollections.observableList(lists));
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (MusicFx.get().getCurrentSong() != null && lists.get(table.getSelectionModel().getSelectedIndex())
                    .toString().equals(MusicFx.get().getCurrentSong().toString())) {
                return;
            }
            MusicFx.get().setList(lists);
            System.out.println("setList");
            MusicFx.get().play(table.getSelectionModel().getSelectedIndex());
            System.gc();
        });
        play.setOnAction(event -> MusicFx.get().playOrPause());
        previous.setOnAction(event -> MusicFx.get().previous());
        next.setOnAction(event -> MusicFx.get().next());
    }

    private void initSetting() {
        SVGGlyph dirFinderGlyph = new SVGGlyph(
                "M20 6h-8l-2-2H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm0 12H4V8h16v10z");
        dirFinderGlyph.setSize(12);
        dirFinder.setGraphic(dirFinderGlyph);
        lists.addAll(SongUtil.getSongs());
        dirFinder.setOnMouseClicked(event -> FXMLController.this.dir
                .setText(new DirectoryChooser().showDialog(dirFinder.getScene().getWindow()).getPath()));
        this.dir.setText(Config.getInstance().getDir());
        Config.getInstance().dirProperty().bind(this.dir.textProperty());
        dir.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                // TODO Auto-generated method stub
                MainApp.initSongs();
            }
        });
    }

    private void initOnline() {
        SVGGlyph searchGlyph = new SVGGlyph(
                "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        searchGlyph.setSize(12);
        search.setGraphic(searchGlyph);
        list.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>() {

            @Override
            public ListCell<Song> call(final ListView<Song> arg0) {
                // TODO Auto-generated method stub
                return new SongOCell();
            }
        });
        search.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                list.getItems().clear();
                songOs = SongUtilO.getSongOs(input.getText());
                list.getItems().addAll(songOs);
            }
        });
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {

            @Override
            public void changed(ObservableValue<? extends Song> arg0, Song arg1, Song arg2) {
                MusicFx.get().setList(songOs);
                MusicFx.get().play(list.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initSetting();
        initMain();
        initLocal();
        initOnline();
    }
}
