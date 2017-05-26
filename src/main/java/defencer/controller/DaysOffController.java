package defencer.controller;

import defencer.service.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Igor Hnes on 21.05.17.
 */
public class DaysOffController implements Initializable {

    @FXML
    private BarChart<String, Long> barChartDaysOff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configure();
    }

    /**
     * Configure bar chart statistics.
     */
    private void configure() {
        final XYChart.Series<String, Long> workDaysStatistic = new XYChart.Series<>();
        workDaysStatistic.setName("Instructors' days off for last year");
        getDaysOffStatistic().forEach((s, b) -> {
            final XYChart.Data<String, Long> data = new XYChart.Data<>(s, b);
            workDaysStatistic.getData().add(data);
        });
        barChartDaysOff.setTitle("Instructors' days off for last year");
        barChartDaysOff.getData().add(workDaysStatistic);
    }

    /**
     * @return instructors names and their days off for last years.
     */
    private Map<String, Long> getDaysOffStatistic() {
        return ServiceFactory.getWiseacreService().getDaysOffStatistic();
    }
}
