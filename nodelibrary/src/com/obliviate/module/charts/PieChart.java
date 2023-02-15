package com.obliviate.module.charts;

import com.obliviate.service.ChartService;
import com.obliviate.service.impl.ChartServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author admin
 */
public class PieChart implements Initializable {

    @FXML
    private javafx.scene.chart.PieChart pieChart;

    private ChartService chartService = new ChartServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //通过service获取数据
        Map<String, Integer> map = chartService.bookTypeCount();
        Data[] dataArray = new Data[map.size()];
        //定义数组下标
        int i = 0;
        for (Map.Entry<String, Integer> next : map.entrySet()) {
            dataArray[i++] = new Data(next.getKey(), next.getValue());
        }
        //将数组转成ObservableList
        ObservableList<Data> pieChartData = FXCollections.observableArrayList(dataArray);
        pieChart.setData(pieChartData);
        pieChart.setClockwise(false);
    }
}
