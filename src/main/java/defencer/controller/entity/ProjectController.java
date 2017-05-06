package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import defencer.controller.AskFormController;
import defencer.controller.CalendarController;
import defencer.controller.PremierLeagueController;
import defencer.controller.update.UpdateProjectController;
import defencer.data.ControllersDataFactory;
import defencer.model.Instructor;
import defencer.model.Project;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import jfxtras.scene.control.ImageViewButton;
import lombok.SneakyThrows;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/13/17.
 */
public class ProjectController implements Initializable {


    @FXML
    private TableView<Project> table;
    @FXML
    private TableColumn<Project, String> name;
    @FXML
    private TableColumn<Project, String> dateStart;
    @FXML
    private TableColumn<Project, String> dateFinish;
    @FXML
    private TableColumn<Project, String> place;
    @FXML
    private TableColumn<Project, String> car;
    @FXML
    private TableColumn<Project, String> instructors;
    @FXML
    private TableColumn<Project, String> description;
    @FXML
    private TableColumn<Project, String> author;
    @FXML
    private JFXComboBox<String> comboProject;
    @FXML
    private JFXButton btnAddInstructor;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private ImageViewButton btnUpdate;
    @FXML
    private JFXDatePicker dateFind;
    @FXML
    private JFXButton btnEditInstructors;

    private ObservableList<Project> observableProjects = FXCollections.observableArrayList();
    private static final Long DEFAULT_PERIOD = 30L;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertProjectTable();
        loadProjects();
        comboProject.setItems(FXCollections
                .observableArrayList(getProjectName()));
        comboProject.setValue("");

        btnAddOneMore.setOnAction(this::newProject);

        btnDelete.setOnAction(e -> deleteProject());

        btnAddInstructor.setOnAction(this::premierLeague);

        btnUpdate.setOnMouseClicked(e -> loadProjects());

        btnFind.setOnAction(e -> search());

        btnEditInstructors.setOnAction(this::editInstructors);

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                editProject(event);
            }
        });
    }

    /**
     * Search project with given parameters.
     */
    private void search() {
        if (dateFind.getValue() == null) {
            dateFind.setValue(LocalDate.now().minusDays(DEFAULT_PERIOD));
        }
        Long days = ChronoUnit.DAYS.between(dateFind.getValue(), LocalDate.now());
        final List<Project> findProject = ServiceFactory.getProjectService().getFindProject(days, comboProject.getValue());
        observableProjects.clear();
        observableProjects.addAll(findProject);
        table.setItems(observableProjects);
    }

    /**
     * Open window for auditing instructors.
     */
    @SneakyThrows
    private void premierLeague(ActionEvent event) {
        final Project project = table.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project first", NotificationUtil.SHORT);
            return;
        } else if (getFreeInstructors().isEmpty()) {
            NotificationUtil.warningAlert("Warning", "All instructors are busy", NotificationUtil.SHORT);
            return;
        }

        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/PremierLeague.fxml"));
        Parent parent = fxmlLoader.load();
        PremierLeagueController premierLeagueController = fxmlLoader.getController();
        premierLeagueController.loadProjectDetails(project);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        value.getStylesheets().add("css/main.css");
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }

    /**
     * Insert value for table.
     */
    private void insertProjectTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("nameId"));
        dateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        dateFinish.setCellValueFactory(new PropertyValueFactory<>("dateFinish"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        car.setCellValueFactory(new PropertyValueFactory<>("car"));
        instructors.setCellValueFactory(new PropertyValueFactory<>("instructors"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
    }

    /**
     * Load projects into table.
     */
    private void loadProjects() {
        observableProjects.clear();
        dateFind.setValue(null);
        observableProjects.addAll(getProject());
        table.setItems(observableProjects);

        ControllersDataFactory.getLink().delete(CalendarController.class);
    }

    /**
     * Open new page to add one more project.
     * {@link SneakyThrows} here because i am totally sure that path to fxml is correct.
     */
    @SneakyThrows
    private void newProject(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/add/NewProject.fxml"));
        Parent parent = fxmlLoader.load();

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editProject(MouseEvent event) {
        final Project project = table.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project first", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/update/UpdateProject.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateProjectController updateProjectController = fxmlLoader.getController();
        updateProjectController.editCurrentProject(project);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> loadProjects());
    }

    /**
     * Edit instructors selected before.
     */
    @SneakyThrows
    private void editInstructors(ActionEvent event) {
        final Project project = table.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project first", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/EditInstructorList.fxml"));
        final Parent parent = fxmlLoader.load();
        EditInstructorListController editInstructorListController = fxmlLoader.getController();
        editInstructorListController.loadInstructors(project);
        final Stage stage = new Stage();
        stage.setTitle("Patriot Defence");
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> loadProjects());

    }

    /**
     * @return list of project for last months.
     */
    private List<Project> getProject() {
        return ServiceFactory.getProjectService().findByPeriod();
    }

    /**
     * Deletes selected project.
     */
    private void deleteProject() {
        final Project project = table.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project first", NotificationUtil.SHORT);
            return;
        }
        try {

            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/askForm.fxml"));
            final Parent parent = fxmlLoader.load();
            final Stage stage = new Stage();
            stage.setTitle("Patriot Defence");
            Scene value = new Scene(parent);
            value.getStylesheets().add("css/main.css");
            stage.setScene(value);
            stage.initModality(Modality.WINDOW_MODAL);
            Window window = btnDelete.getScene().getWindow();
            stage.initOwner(window);
            stage.show();

            stage.setOnHiding(event -> {
                if ((boolean) ControllersDataFactory.getLink().get(AskFormController.class, "isDelete")) {
                    try {
                        ServiceFactory.getProjectService().deleteEntity(project);
                    } catch (Exception e) {
                        NotificationUtil.errorAlert("Error", "Can't delete", NotificationUtil.SHORT);
                    }
                    observableProjects.clear();
                    loadProjects();
                }
            });

        } catch (Exception e) {
            NotificationUtil.errorAlert("Error", "Can't delete", NotificationUtil.SHORT);
        }
    }

    /**
     * @return all type of available projects.
     */
    private List<String> getProjectName() {
        return ServiceFactory.getWiseacreService().getAvailableProject();
    }

    /**
     * @return free instructor's name for project.
     */
    private List<Instructor> getFreeInstructors() {
        return ServiceFactory.getWiseacreService().getFreeInstructors();
    }
}
