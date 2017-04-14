package defencer.controller.home;

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
    private PieChart pieChartProject;
    @FXML
    private PieChart pieChartInstructor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<PieChart.Data> pieChartDataProject =
                FXCollections.observableArrayList(
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value));

                pieChartProject.setData(pieChartDataProject);
        pieChartProject.setTitle("Project Statistic");

        ObservableList<PieChart.Data> pieChartDataInstructor =
                FXCollections.observableArrayList(
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value),
                        new PieChart.Data("????", value));

                pieChartInstructor.setData(pieChartDataInstructor);
        pieChartInstructor.setTitle("Instructor Statistic");
    }
}
