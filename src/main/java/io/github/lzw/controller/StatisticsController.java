/*
 * @Author: lzw-723
 * @Date: 2020-04-17 19:54:57
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-19 10:03:06
 * @Description: 描述信息
 * @FilePath: \MusicFXSingleton\src\main\java\io\github\lzw\controller\StatisticsController.java
 */
package io.github.lzw.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import io.github.lzw.bean.Record;
import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongL;
import io.github.lzw.util.StatisticsUtil;
import io.github.lzw.util.TimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
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
        int daytime = 0;
        int night = 0;
        HashMap<String, Integer> count = new HashMap<>();
        for (Record record : StatisticsUtil.getRecords()) {
            if (TimeFormatter.isDaytime(record.getDate())) {
                daytime++;
            }
            else{
                night++;
            }
            String day = TimeFormatter.getDay(record.getDate());
            if (count.get(day) == null) {
                count.put(day, 0);
            }
            count.put(day, count.get(day) + 1);
        }
        ObservableList<Data> datas = FXCollections.observableArrayList();
        datas.add(new Data("昼", daytime));
        datas.add(new Data("夜", night));
        pieChart.setData(datas);

        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        Series<String, Double> aSeries = new Series<String, Double>();
        aSeries.setName("听歌数");
        
        for (int i = 0; i < count.keySet().size(); i++) {
            aSeries.getData().add(new XYChart.Data(String.valueOf(i), i));
            // aValue = aValue + Math.random() * 100 - 50;
        }
        answer.addAll(aSeries);
        lineChart.setData(answer);
    }

    @Override
    public String getTitle() {
        return "数据统计";
    }

    @Override
    public ControllerImpl getController() {
        return null;
    }

    @Override
    public void onPlay(Song song) {

    }

}