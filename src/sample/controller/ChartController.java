package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import sample.Client;
import sample.model.Model;
import sample.objects.Cofe;
import sample.objects.Order;
import sample.objects.User;

import java.io.IOException;
import java.util.ArrayList;

public class ChartController {
    public PieChart chart;
    private Model modelObj;
    private ArrayList<Cofe> cofes;

    @FXML
    public void initialize(){

    }

    public void setInformation(ObservableList<Order> items) {

        int time_1 = 0, time_2 = 0, time_3 = 0, time_4 = 0,
        time_5 = 0, time_6 = 0, time_7 = 0, time_8 = 0, time_9 = 0, time_10 = 0;

        for(Order order : items){
            switch (order.getTime()){
                case "8 : 00":
                    time_1 += order.getCountCofe();
                    break;
                case "9 : 00":
                    time_2 += order.getCountCofe();
                    break;
                case "10 : 00":
                    time_3 += order.getCountCofe();
                    break;
                case "11 : 00":
                    time_4 += order.getCountCofe();
                    break;
                case "12 : 00":
                    time_5 += order.getCountCofe();
                    break;
                case "13 : 00":
                    time_6 += order.getCountCofe();
                    break;
                case "14 : 00":
                    time_7 += order.getCountCofe();
                    break;
                case "15 : 00":
                    time_8 += order.getCountCofe();
                    break;
                case "16 : 00":
                    time_9 += order.getCountCofe();
                    break;
                case "17 : 00":
                    time_10 += order.getCountCofe();
                    break;
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("8 : 00", time_1),
                new PieChart.Data("9 : 00", time_2),
                new PieChart.Data("10 : 00", time_3),
                new PieChart.Data("11 : 00", time_4),
                new PieChart.Data("12 : 00", time_5),
                new PieChart.Data("13 : 00", time_6),
                new PieChart.Data("14 : 00", time_7),
                new PieChart.Data("15 : 00", time_8),
                new PieChart.Data("16 : 00", time_9),
                new PieChart.Data("17 : 00", time_10)
        );
        chart.setData(pieChartData);
    }
}
