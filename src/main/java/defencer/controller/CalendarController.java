package defencer.controller;

import defencer.data.ControllersDataFactory;
import defencer.hibernate.HibernateQueryBuilder;
import defencer.hibernate.HibernateService;
import defencer.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.agenda.Agenda;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Nikita on 30.04.2017.
 */
public class CalendarController implements Initializable {

    @FXML
    private Agenda agenda;
    @FXML
    private AnchorPane pervWeek;
    @FXML
    private AnchorPane nextWeek;
    @FXML
    private GridPane leftSide;
    @FXML
    private GridPane rightSide;
    //don't pay attention on this shit,it is all because of this **** checkstyle plugin =(
    private final int countOfGroups = 10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        factoryInitialization();

        weekButtonsInitialization();
    }


    /**
     * Getting or adding data to factory.
     */
    private void factoryInitialization() {

        List<Project> data = null;

        if (ControllersDataFactory.getLink().contains(CalendarController.class)) {
            data = (List<Project>) ((Map<String, Object>) ControllersDataFactory.getLink().get(CalendarController.class)).get("agendaData");
        } else {
            data = downloadData();

            Map<String, Object> map = new HashMap<>();
            map.put("agendaData", data);

            ControllersDataFactory.getLink().add(this.getClass(), map);
        }

        addDataOnView(data);
    }


    /*private void update() {

        List<Project> data = downloadData();

        addDataOnView(data);
    }*/


    /**
     * Downloading data from database.
     */
    private List<Project> downloadData() {

        HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, Project.class);

        return (List<Project>) HibernateService.executeQuery(hibernateQueryBuilder);
    }


    /**
     * Adding data on view.
     */
    private void addDataOnView(List<Project> data) {

        for (Project project : data) {

            LocalDate start = project.getDateStart();
            LocalDate end = project.getDateFinish();

            LocalTime timeStart = LocalTime.MIN;
            LocalTime timeEnd = LocalTime.MAX;

            agenda.appointments().addAll(
                    new Agenda.AppointmentImplLocal()
                            .withStartLocalDateTime(LocalDateTime.of(start, timeStart))
                            .withEndLocalDateTime(LocalDateTime.of(end, timeEnd))
                            .withSummary(project.getName())
                            .withWholeDay(true)
                            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + new Random().nextInt(countOfGroups))) // you should use a map of AppointmentGroups
            );
        }
    }


    /**
     * Initialization of week buttons.
     */
    private void weekButtonsInitialization() {

        for (Node temp : pervWeek.getChildren()) {
            temp.setVisible(false);
        }

        leftSide.setOnMouseEntered(event -> {
            for (Node temp : pervWeek.getChildren()) {
                temp.setVisible(true);
            }
        });

        leftSide.setOnMouseExited(event -> {
            for (Node temp : pervWeek.getChildren()) {
                temp.setVisible(false);
            }
        });

        pervWeek.setOnMouseClicked(event -> {
            agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().minusWeeks(1L));
        });


        for (Node temp : nextWeek.getChildren()) {
            temp.setVisible(false);
        }

        rightSide.setOnMouseEntered(event -> {
            for (Node temp : nextWeek.getChildren()) {
                temp.setVisible(true);
            }
        });

        rightSide.setOnMouseExited(event -> {
            for (Node temp : nextWeek.getChildren()) {
                temp.setVisible(false);
            }
        });

        nextWeek.setOnMouseClicked(event -> {
            agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().plusWeeks(1L));
        });

    }
}