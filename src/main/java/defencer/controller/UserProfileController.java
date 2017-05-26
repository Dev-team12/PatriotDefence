package defencer.controller;

import com.cathive.fx.gravatar.GravatarUrlBuilder;
import com.cathive.fx.gravatar.Rating;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import defencer.data.ControllersDataFactory;
import defencer.data.CurrentUser;
import defencer.model.*;
import defencer.service.InstructorService;
import defencer.service.WiseacreService;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Nikita on 14.04.2017.
 */
public class UserProfileController implements Initializable {

    @FXML
    private JFXButton btnSure;
    @FXML
    private JFXButton btnCanNot;
    @FXML
    private JFXButton btnChangePassword;
    @FXML
    private TableView<Schedule> tableMyProjects;
    @FXML
    private TableView<DaysOff> tableDaysOff;
    @FXML
    private TableColumn<DaysOff, LocalDate> busyFrom;
    @FXML
    private TableColumn<DaysOff, LocalDate> busyTo;
    @FXML
    private TableColumn<Project, LocalDate> myFinishDate;
    @FXML
    private TableColumn<Project, LocalDate> myStartDate;
    @FXML
    private TableColumn<Project, String> myProjectName;
    @FXML
    private TableColumn<Project, String> status;
    @FXML
    private ImageView userImage;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton btnSetDaysBusy;
    @FXML
    private JFXButton btnDeleteDayOff;
    @FXML
    private JFXButton btnUpdateProfile;
    @FXML
    private JFXDatePicker dateBusyFrom;
    @FXML
    private JFXDatePicker dateBusyTo;

    private ObservableList<DaysOff> observableDaysOff = FXCollections.observableArrayList();
    private ObservableList<Schedule> observableMyProject = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CurrentUser.getLink().refresh(CurrentUser.getLink().getEmail());

        final GravatarUrlBuilder gravatarUrlBuilder = GravatarUrlBuilder.create();
        final int size = 262;
        final URL build = gravatarUrlBuilder.email(CurrentUser.getLink().getEmail())
                .rating(Rating.GENERAL_AUDIENCES)
                .size(size)
                .build();
        userImage.setImage(new Image(build.toExternalForm(), true));
        factoryInitialization();

        insertTables();
        loadTableDaysOff();
        loadTableMyProject();

        MainActivityController mainActivityController = (MainActivityController) ControllersDataFactory.getLink().get(MainActivityController.class, "class");
        mainActivityController.hideSmartToolbar();

        btnSetDaysBusy.setOnAction(e -> newDayOff());
        btnDeleteDayOff.setOnAction(e -> deleteDayOff());
        btnUpdateProfile.setOnAction(s -> updateProfile());
        btnCanNot.setOnAction(e -> disagree());
        btnSure.setOnAction(e -> agree());
        btnChangePassword.setOnAction(this::changePassword);
    }

    /**
     * Update user profile.
     */
    private void updateProfile() {
        final InstructorService instructorService = ServiceFactory.getInstructorService();
        final Instructor instructor = instructorService.findByEmail(CurrentUser.getLink().getEmail());
        instructor.setFirstName(firstName.getText());
        instructor.setLastName(lastName.getText());
        instructor.setPhone(phone.getText());
        instructor.setEmail(email.getText());
        try {
            instructorService.updateEntity(instructor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NotificationUtil.informationAlert("Success", "Your profile has been updated", NotificationUtil.SHORT);
    }

    /**
     * Add new day off.
     */
    private void newDayOff() {
        if (dateBusyFrom.getValue() == null || dateBusyTo.getValue() == null) {
            return;
        }
        ServiceFactory.getWiseacreService()
                .addNewDaysOff(CurrentUser.getLink().getId(), dateBusyFrom.getValue(), dateBusyTo.getValue(), tableMyProjects.getItems());
        dateBusyFrom.setValue(null);
        dateBusyTo.setValue(null);
        loadTableDaysOff();
    }

    /**
     * Delete selected day off.
     */
    private void deleteDayOff() {
        final DaysOff daysOff = tableDaysOff.getSelectionModel().getSelectedItem();
        if (daysOff == null) {
            return;
        }
        try {
            ServiceFactory.getWiseacreService().deleteEntity(daysOff);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadTableDaysOff();
    }

    /**
     * Insert days off tableDaysOff.
     */
    private void insertTables() {
        busyFrom.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        busyTo.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
        myProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        myStartDate.setCellValueFactory(new PropertyValueFactory<>("startProject"));
        myFinishDate.setCellValueFactory(new PropertyValueFactory<>("finishProject"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    /**
     * Load days off into tableDaysOff.
     */
    private void loadTableDaysOff() {
        observableDaysOff.clear();
        observableDaysOff.addAll(getDaysOfForCurrentUser());
        tableDaysOff.setItems(observableDaysOff);
    }

    /**
     * Load my project.
     */
    private void loadTableMyProject() {
        observableMyProject.clear();
        observableMyProject.addAll(getMyProjects());
        tableMyProjects.setItems(observableMyProject);
    }

    /**
     * @return list of days off for current user.
     */
    private List<DaysOff> getDaysOfForCurrentUser() {
        return ServiceFactory.getWiseacreService().getDaysOff(CurrentUser.getLink().getId());
    }

    /**
     * @return list of project for current user.
     */
    private List<Schedule> getMyProjects() {
        return ServiceFactory.getInstructorService().getMyProject(CurrentUser.getLink().getId());
    }

    /**
     * Set status free for instructor if he cat't take the project.
     */
    private void disagree() {
        final Schedule schedule = tableMyProjects.getSelectionModel().getSelectedItem();
        if (schedule == null || "Confirmed".equals(schedule.getStatus())) {
            return;
        }
        ServiceFactory.getWiseacreService().deleteSelectedInstructors(CurrentUser.getLink().getId(), schedule.getProjectId());
        CurrentUser.refresh(CurrentUser.getLink().getEmail());
        loadTableMyProject();
    }

    /**
     * Set status busy if instructor take the project.
     */
    private void agree() {
        final Schedule schedule = tableMyProjects.getSelectionModel().getSelectedItem();
        if (schedule == null || "Confirmed".equals(schedule.getStatus())) {
            return;
        }
        final WiseacreService wiseacreService = ServiceFactory.getWiseacreService();
        wiseacreService.updateSchedule(CurrentUser.getLink(), schedule);
        try {
            wiseacreService.setWorksDays(CurrentUser.getLink().getId(), schedule.getStartProject(), schedule.getFinishProject());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadTableMyProject();
    }

    /**
     * Setting data of current user.
     */
    private void factoryInitialization() {
        CurrentUser currentUser = CurrentUser.getLink();
        firstName.setText(currentUser.getFirstName());
        lastName.setText(currentUser.getLastName());
        phone.setText(currentUser.getPhoneNumber());
        email.setText(currentUser.getEmail());
    }

    /**
     * Change password for current user.
     */
    @SneakyThrows
    private void changePassword(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/NewPass.fxml"));
        Parent parent = fxmlLoader.load();
        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        value.getStylesheets().add("css/main.css");
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }
}