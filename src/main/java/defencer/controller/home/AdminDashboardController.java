package defencer.controller.home;

import defencer.service.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

/**
 * @author Igor Gnes on 4/13/17.
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private BarChart<String, Long>  barChart;
    @FXML
    private Text totalInstructors;
    @FXML
    private Text totalApprentice;
    @FXML
    private Text projectForMonths;
    @FXML
    private Text apprenticeForMonths;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final XYChart.Series<String, Long> statisticData = new XYChart.Series<>();
        statisticData.setName("Instructors");
        getInstructorStatisticForAdminDashBoard().forEach((s, b) -> {
            final XYChart.Data<String, Long> data = new XYChart.Data<>(s, b);
            statisticData.getData().add(data);
        });
        barChart.setTitle("Instructor statistic");
        barChart.getData().add(statisticData);
    }

    /**
     * @return all type of available projects and how many time they were created for last months.
     */
    private Map<String, Long> getProjectStatisticForAdminDashBoard() {
        return ServiceFactory.getWiseacreService().getProjectStatistic();
    }

    /**
     * @return all instructors and how many time they worked for last months.
     */
    private Map<String, Long> getInstructorStatisticForAdminDashBoard() {
        return ServiceFactory.getWiseacreService().getInstructorStatistic();
    }
}
