package io.github.lzw;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongO;
import io.github.lzw.item.SongOCell;
import io.github.lzw.util.SongInfoUtil;
import io.github.lzw.util.SongUtil;
import io.github.lzw.util.SongUtilO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import lzw.musicol.core.Musicol;

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
    private Slider slider1;
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

    private void initMain() {
        slider1.setOnTouchMoved(new EventHandler<TouchEvent>() {
            @Override
            public void handle(TouchEvent event) {
                System.out.println(event.toString());
                Musicol.get().getMediaPlayer()
                        .seek(new Duration(slider1.getValue() * Musicol.get().getTotalTime() * 1000d));
            }
        });
        Musicol.get().volumeProperty().bind(slider2.valueProperty());
    }

    private void initLocal() {
        table.getColumns().get(0).prefWidthProperty().bind(table.widthProperty().divide(4));
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("title"));
        table.getColumns().get(1).prefWidthProperty().bind(table.widthProperty().divide(4));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("artist"));
        table.getColumns().get(2).prefWidthProperty().bind(table.widthProperty().divide(4));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("album"));
        table.getColumns().get(3).prefWidthProperty().bind(table.widthProperty().divide(4));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("length"));
        table.getItems().addAll(FXCollections.observableList(lists));
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Musicol.get().play(newValue.getUri());
            title.setText(newValue.getTitle());
            try {
                Image image = SongInfoUtil.getArtWork(new File(new URI(newValue.getUri())));
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Musicol.get().currentProgressProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    slider1.setValue(newValue.doubleValue());
                }
            });
        });
    }

    private void initSetting() {
        File dir = new File(Config.getInstance().getDir());
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
            public ListCell<SongO> call(ListView<SongO> arg0) {
                // TODO Auto-generated method stub
                return new SongOCell();
            }
        });
        search.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                list.getItems().clear();
                list.getItems().addAll(FXCollections.observableArrayList(SongUtilO.getSongOs(input.getText())));
            }
        });
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SongO>() {

            @Override
            public void changed(ObservableValue<? extends SongO> arg0, SongO arg1, SongO arg2) {
                try {
                    Musicol.get().play(new URL(arg2.getUrl()).toURI().toString());
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
