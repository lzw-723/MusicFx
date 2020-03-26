package io.github.lzw;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.svg.SVGGlyph;

import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongO;
import io.github.lzw.core.MusicFx;
import io.github.lzw.core.MusicFx.Handler;
import io.github.lzw.item.SongCell;
import io.github.lzw.item.SongOCell;
import io.github.lzw.util.SongInfoUtil;
import io.github.lzw.util.SongUtil;
import io.github.lzw.util.SongUtilO;
import io.github.lzw.util.TimeFormater;
import javafx.beans.property.SimpleLongProperty;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private Button play;
    @FXML
    private HBox layout;
    @FXML
    private Slider slider2;
    @FXML
    private TextField input;
    @FXML
    private Button search;
    @FXML
    private ListView<SongO> list;
    @FXML
    private Button dirFinder;
    @FXML
    private TextField dir;
    private ArrayList<Song> lists = new ArrayList<>();
    private List<SongO> songOs = new ArrayList<>();

    private void initMain() {
        // layout.getChildren().add(new SVGGlyph("M8 5v14l11-7z"));

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
            public void onStart(final Song song) {
                // TODO Auto-generated method stub
                title.setText(song.getTitle());
                artist.setText(song.getArtist());
                try {
                    Image image;
                    if (song.isLocal()) {
                        image = SongInfoUtil.getArtWork(new File(new URI(song.getUri())));
                    } else {
                        image = new Image(song.getPic());
                    }

                    imageView.setImage(image);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPause() {
                // TODO Auto-generated method stub

            }

            @Override
            public void init() {
                // TODO Auto-generated method stub

            }

            @Override
            public void OnEnd() {
                // TODO Auto-generated method stub
                final int index = table.getSelectionModel().getSelectedIndex() + 1;
                table.getSelectionModel().select(index);
            }
        });
    }

    private void initLocal() {
        table.getColumns().get(0).prefWidthProperty().bind(table.widthProperty().divide(4));
        ((TableColumn<Song, String>) table.getColumns().get(0))
                .setCellValueFactory(arg0 -> arg0.getValue().titleProperty());
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
                .setCellValueFactory(arg0 -> arg0.getValue().artistProperty());
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
                .setCellValueFactory(arg0 -> arg0.getValue().albumProperty());
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
                .setCellValueFactory(arg0 -> arg0.getValue().lengthProperty());
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
            MusicFx.get().setList(lists);
            MusicFx.get().play(table.getSelectionModel().getSelectedIndex());
        });
        play.setOnAction(event -> MusicFx.get().playOrPause());
    }

    private void initSetting() {
        final File dir = new File(Config.getInstance().getDir());
        if (SongUtil.getSongs(dir) != null)
            lists.addAll(SongUtil.getSongs(dir));
        dirFinder.setOnMouseClicked(event -> FXMLController.this.dir
                .setText(new DirectoryChooser().showDialog(dirFinder.getScene().getWindow()).getPath()));
        this.dir.setText(Config.getInstance().getDir());
        Config.getInstance().dirProperty().bind(this.dir.textProperty());
    }

    private void initOnline() {
        list.setCellFactory(new Callback<ListView<SongO>, ListCell<SongO>>() {

            @Override
            public ListCell<SongO> call(final ListView<SongO> arg0) {
                // TODO Auto-generated method stub
                return new SongOCell();
            }
        });
        search.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent arg0) {
                list.getItems().clear();
                songOs = SongUtilO.getSongOs(input.getText());
                list.getItems().addAll(FXCollections.observableArrayList());
            }
        });
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SongO>() {

            @Override
            public void changed(final ObservableValue<? extends SongO> arg0, final SongO arg1, final SongO arg2) {
                MusicFx.get().setList(SongUtilO.SongO2song(songOs));
                MusicFx.get().play(list.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        initSetting();
        initMain();
        initLocal();
        initOnline();
    }
}
