package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.controller.update.UpdateProjectController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
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
    private TableColumn<Project, String> dataStart;
    @FXML
    private TableColumn<Project, String> dataFinish;
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
    private JFXButton btnEdit;

    private ObservableList<Project> observableProjects = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertProjectTable();
        loadProjects();
        comboProject.setItems(FXCollections
                .observableArrayList(getProjectName()));

        btnAddOneMore.setOnAction(e -> newProject());

        btnDelete.setOnAction(e -> deleteProject());

        btnEdit.setOnAction(this::editProject);

        table.setOnMouseClicked(e -> {
            if (e.getClickCount() >= 2) {
                // todo edit project
            }
        });
        btnAddInstructor.setOnAction(this::premierLeague);
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
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/PremierLeague.fxml"));
        Parent parent = fxmlLoader.load();
        PremierLeagueController premierLeagueController = fxmlLoader.getController();
        premierLeagueController.loadProjectDetails(project);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }

    /**
     * Insert value for table.
     */
    private void insertProjectTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataStart.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        dataFinish.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
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
        observableProjects.addAll(getProject());
        table.setItems(observableProjects);
    }

    /**
     * Open new page to add one more project.
     *
     * {@link SneakyThrows} here because i am totally sure that path to fxml is correct.
     */
    @SneakyThrows
    private void newProject() {
        Parent root = FXMLLoader.load(getClass().getResource("/entity/add/NewProject.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editProject(ActionEvent event) {
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
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }

    /**
     * @return list of project for last months.
     */
    private List<Project> getProject() {
        return ServiceFactory.getProjectService().getProjectsForLastMonths();
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
            ServiceFactory.getProjectService().deleteEntity(project);
            observableProjects.clear();
            loadProjects();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return all type of available projects.
     */
    private List<String> getProjectName() {
        return ServiceFactory.getWiseacreService().getAvailableProject();
    }
}