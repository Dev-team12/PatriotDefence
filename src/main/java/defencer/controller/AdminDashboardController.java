package defencer.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/13/17.
 */
public class AdminDashboardController implements Initializable {

    private final int value = 12;

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value));

                pieChart.setData(pieChartData);
        pieChart.setTitle("Statistic");
    }
}
