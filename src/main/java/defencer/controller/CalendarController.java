package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.hibernate.HibernateQueryBuilder;
import defencer.hibernate.HibernateService;
import defencer.model.Event;
import defencer.model.Project;
import defencer.util.NotificationUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    @FXML
    private JFXButton btnAddEvent;

    private static final int COUNT_OF_GROUPS = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MainActivityController mainActivityController = (MainActivityController) ControllersDataFactory.getLink().get(MainActivityController.class, "class");
        mainActivityController.hideSmartToolbar();

        mainActivityController.getBtnAddEvent().setVisible(true);
        mainActivityController.getBtnAddEvent().setOnMouseClicked(e -> addEvent());
        factoryInitialization();
        weekButtonsInitialization();
    }

    /**
     * Getting or adding data to factory.
     */
    private void factoryInitialization() {
        addEventOnView(downloadEvents());
        addProjectOnView(downloadProjects());
        addDaysOffOnView(downloadDaysOff());
    }

    /**
     * Update calendar.
     */
    private void update() {
        agenda.appointments().clear();
        addEventOnView(downloadEvents());
        addProjectOnView(downloadProjects());
        addDaysOffOnView(downloadDaysOff());
    }

    /**
     * Downloading projects from database.
     */
    private List<Project> downloadProjects() {
        HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, Project.class);
        return (List<Project>) HibernateService.executeQuery(hibernateQueryBuilder);
    }

    /**
     * Downloading events from database.
     */
    private List<Event> downloadEvents() {
        HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, Event.class);
        return (List<Event>) HibernateService.executeQuery(hibernateQueryBuilder);
    }

    /**
     * Downloading events from database.
     */
    private List<DaysOff> downloadDaysOff() {
        HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, DaysOff.class);
        return (List<DaysOff>) HibernateService.executeQuery(hibernateQueryBuilder);
    }

    /**
     * Add events to calendar.
     */
    private void addEventOnView(List<Event> events) {

        events.forEach(s -> {

            Agenda.AppointmentImplLocal appointmentImplLocal = new Agenda.AppointmentImplLocal();

            appointmentImplLocal
                    .withSummary(s.getName())
                    .withStartLocalDateTime(s.getDate())
                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + new Random().nextInt(COUNT_OF_GROUPS)));

            agenda.appointments().addAll(appointmentImplLocal);
        });
    }

    /**
     * Add DaysOff to calendar.
     */
    private void addDaysOffOnView(List<DaysOff> daysOff) {

        LocalTime timeStart = LocalTime.MIN;
        LocalTime timeEnd = LocalTime.MAX;

        daysOff.forEach(s -> {

            LocalDate start = s.getDateFrom();
            LocalDate end = s.getDateTo();

            HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, Instructor.class);
            hibernateQueryBuilder.with(HibernateQueryBuilder.ID_FIELD, s.getInstructorId());

            Instructor instructor = (Instructor) HibernateService.executeQuery(hibernateQueryBuilder).get(0);

            Agenda.AppointmentImplLocal appointmentImplLocal = new Agenda.AppointmentImplLocal();

            appointmentImplLocal.withStartLocalDateTime(LocalDateTime.of(start, timeStart))
                    .withEndLocalDateTime(LocalDateTime.of(end, timeEnd))
                    .withWholeDay(true)
                    .withSummary("Day off for " + instructor.getFirstLastName())
                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + new Random().nextInt(COUNT_OF_GROUPS)));

            agenda.appointments().addAll(appointmentImplLocal);
        });
    }

    /**
     * Add project to calendar.
     */
    private void addProjectOnView(List<Project> projects) {

        LocalTime timeStart = LocalTime.MIN;
        LocalTime timeEnd = LocalTime.MAX;

        projects.forEach(s -> {
            LocalDate start = s.getDateStart();
            LocalDate end = s.getDateFinish();

            Agenda.AppointmentImplLocal appointmentImplLocal = new Agenda.AppointmentImplLocal();

            appointmentImplLocal.withStartLocalDateTime(LocalDateTime.of(start, timeStart))
                    .withEndLocalDateTime(LocalDateTime.of(end, timeEnd))
                    .withWholeDay(true)
                    .withSummary(s.getNameId())
                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + new Random().nextInt(COUNT_OF_GROUPS)));

            agenda.appointments().addAll(appointmentImplLocal);
        });
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

        pervWeek.setOnMouseClicked(event -> agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().minusWeeks(1L)));


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
        nextWeek.setOnMouseClicked(event -> agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().plusWeeks(1L)));
    }


    /**
     * Call window for creating new Event.
     */
    private void addEvent() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/entity/add/NewEvent.fxml"));
            final Parent parent = fxmlLoader.load();
            final Stage stage = new Stage();
            stage.setTitle("Patriot Defence");
            Scene value = new Scene(parent);
            value.getStylesheets().add("css/main.css");
            stage.setScene(value);
            stage.initModality(Modality.WINDOW_MODAL);
            Window window = rightSide.getScene().getWindow();
            stage.initOwner(window);
            stage.show();

            stage.setOnHiding(event -> update());

        } catch (Exception e) {
            NotificationUtil.errorAlert("Error", "Can't open", NotificationUtil.SHORT);
        }
    }
}
