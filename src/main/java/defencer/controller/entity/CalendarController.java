package defencer.controller.entity;

import defencer.data.ControllersDataFactory;
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
import javafx.scene.control.Button;
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
    private Button add;

    private static final int COUNT_OF_GROUPS = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       /* ImageView add = (ImageView) ControllersDataFactory.getLink().get(ControllersDataFactory.class,"add");
        add.setOnMouseClicked(event -> {
            System.out.println("WORK WORK WORK");
        });*/

        add.setOnMouseClicked(this::addEvent);

        factoryInitialization();

        weekButtonsInitialization();
    }

    /**
     * Getting or adding data to factory.
     */
    private void factoryInitialization() {

        List data;

        if (ControllersDataFactory.getLink().contains(CalendarController.class)) {
            data = (List<Project>) ((Map<String, Object>) ControllersDataFactory.getLink().get(CalendarController.class)).get("agendaData");
        } else {
            data = new LinkedList<>();
            List projects = downloadProjects();
            if (projects != null) {
                data.addAll(projects);
            }

            List events = downloadEvents();
            if (projects != null) {
                data.addAll(events);
            }
            data.addAll(downloadEvents());

            Map<String, Object> map = new HashMap<>();
            map.put("agendaData", data);

            ControllersDataFactory.getLink().add(this.getClass(), map);
        }
        addDataOnView(data);
    }

    /**
     * Update calendar.
     */
    private void update() {

        List data = new LinkedList<>();

        List projects = downloadProjects();
        if (projects != null) {
            data.addAll(projects);
        }

        List events = downloadEvents();
        if (projects != null) {
            data.addAll(events);
        }
        data.addAll(downloadEvents());

        addDataOnView(data);
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
     * Adding data on view.
     */
    private void addDataOnView(List data) {

        for (Object object : data) {

            Agenda.AppointmentImplLocal appointmentImplLocal = null;

            LocalTime timeStart = LocalTime.MIN;
            LocalTime timeEnd = LocalTime.MAX;

            if (object.getClass().equals(Project.class)) {
                Project project = (Project) object;

                LocalDate start = project.getDateStart();
                LocalDate end = project.getDateFinish();

                appointmentImplLocal = new Agenda.AppointmentImplLocal();

                appointmentImplLocal.withStartLocalDateTime(LocalDateTime.of(start, timeStart))
                        .withEndLocalDateTime(LocalDateTime.of(end, timeEnd))
                        .withWholeDay(true)
                        .withSummary(project.getName())
                        .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + new Random().nextInt(COUNT_OF_GROUPS)));


            } else if (object.getClass().equals(Event.class)) {
                Event event = (Event) object;

                LocalDate start = event.getDate();

                appointmentImplLocal = new Agenda.AppointmentImplLocal();

                appointmentImplLocal.withStartLocalDateTime(LocalDateTime.of(start, timeStart))
                        .withWholeDay(true)
                        .withSummary(event.getName())
                        .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group" + new Random().nextInt(COUNT_OF_GROUPS)));
            }


            agenda.appointments().addAll(appointmentImplLocal);
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
    private void addEvent(MouseEvent mouseEvent) {
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
            Window window = add.getScene().getWindow();
            stage.initOwner(window);
            stage.show();

            stage.setOnHiding(event -> {
                update();
            });

        } catch (Exception e) {
            NotificationUtil.errorAlert("Error", "Can't open", NotificationUtil.SHORT);
        }
    }
}
