/*
 * @Author: lzw-723
 * @Date: 2020-04-17 19:54:57
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-18 09:02:28
 * @Description: 描述信息
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\controller\StatisticsController.java
 */
package io.github.lzw.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart.Series;

public class StatisticsController implements Initializable, ControllerImpl {

    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private BarChart<SongL, Integer> barChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO Auto-generated method stub
        ObservableList<Data> datas = FXCollections.observableArrayList();
        datas.add(new Data("昼", 0.7));
        datas.add(new Data("夜", 0.9));
        pieChart.setData(datas);
        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        Series<String, Double> aSeries = new Series<String, Double>();
        aSeries.setName("a");

        for (int i = 2001; i < 2021; i++) {
            aSeries.getData().add(new XYChart.Data(Integer.toString(i), i));
            // aValue = aValue + Math.random() * 100 - 50;
        }
        answer.addAll(aSeries);
        lineChart.setData(answer);
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "数据统计";
    }

    @Override
    public ControllerImpl getController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void play(Song song) {
        // TODO Auto-generated method stub

    }

}