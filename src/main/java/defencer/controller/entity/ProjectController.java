package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXNodesList;
import defencer.controller.AskFormController;
import defencer.controller.CalendarController;
import defencer.controller.MainActivityController;
import defencer.controller.PremierLeagueController;
import defencer.controller.update.UpdateProjectController;
import defencer.data.ControllersDataFactory;
import defencer.model.Car;
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
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/13/17.
 */
@RequiredArgsConstructor
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
    private TableColumn<Project, String> refusal;
    @FXML
    private TableColumn<Project, String> expected;
    @FXML
    private JFXComboBox<String> comboProject;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnConfigureProject;
    @FXML
    private JFXNodesList nodeList;
    @FXML
    private JFXDatePicker dateFind;


    private ObservableList<Project> observableProjects = FXCollections.observableArrayList();
    private static final Long DEFAULT_PERIOD = 30L;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        insertProjectTable();
        loadProjects();
        comboProject.setItems(FXCollections
                .observableArrayList(getProjectName()));
        comboProject.setValue("");


        MainActivityController mainActivityController = (MainActivityController) ControllersDataFactory.getLink().get(MainActivityController.class, "class");
        mainActivityController.showSmartToolbar();

        mainActivityController.getAddAction().setOnMouseClicked(this::newProject);
        mainActivityController.getDeleteAction().setOnMouseClicked(e -> deleteProject());
        mainActivityController.getUpdateAction().setOnMouseClicked(e -> loadProjects());
        mainActivityController.getBtnAddEvent().setVisible(false);
        mainActivityController.getPdfExportAction().setOnMouseClicked(e -> pdfReport());
        mainActivityController.getBtnExcel().setOnMouseClicked(e -> excelReport());

        btnFind.setOnAction(e -> search());

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                editProject(event);
            }
        });

        projectConfigure();
    }

    /**
     * Configure project, add, edit cars and instructors.
     */
    private void projectConfigure() {

        btnConfigureProject.setButtonType(JFXButton.ButtonType.RAISED);

        final JFXButton btnEditInstructor = new JFXButton("Edit instructor");
        btnEditInstructor.getStyleClass().add("button-try-now");

        final JFXButton btnAddCar = new JFXButton("Configure car");
        btnAddCar.getStyleClass().add("button-try-now");

        final JFXButton btnAddInstructor = new JFXButton("Add Instructor");
        btnAddInstructor.getStyleClass().add("button-try-now");

        final int value = 10;
        nodeList.setSpacing(value);
        nodeList.addAnimatedNode(btnConfigureProject);
        nodeList.addAnimatedNode(btnAddInstructor);
        nodeList.addAnimatedNode(btnEditInstructor);
        nodeList.addAnimatedNode(btnAddCar);

        btnAddInstructor.setOnAction(this::premierLeague);
        btnEditInstructor.setOnAction(this::editInstructors);
        btnAddCar.setOnAction(this::addCar);
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
        }
        final List<Instructor> freeInstructors = getFreeInstructors(project);
        if (freeInstructors.isEmpty()) {
            NotificationUtil.warningAlert("Warning", "All instructors are busy", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/PremierLeague.fxml"));
        Parent parent = fxmlLoader.load();
        PremierLeagueController premierLeagueController = fxmlLoader.getController();
        premierLeagueController.loadProjectDetails(project, freeInstructors);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        value.getStylesheets().add("css/main.css");
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> loadProjects());
    }

    /**
     * Insert value for table.
     */
    private void insertProjectTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("nameId"));
        dateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        dateFinish.setCellValueFactory(new PropertyValueFactory<>("dateFinish"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        car.setCellValueFactory(new PropertyValueFactory<>("cars"));
        instructors.setCellValueFactory(new PropertyValueFactory<>("instructors"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        refusal.setCellValueFactory(new PropertyValueFactory<>("refusal"));
        expected.setCellValueFactory(new PropertyValueFactory<>("expected"));
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
    private void newProject(MouseEvent event) {

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

        stage.setOnHiding(e -> loadProjects());
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
     * Add delete car for selected project.
     */
    @SneakyThrows
    private void addCar(ActionEvent event) {
        final Project project = table.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project first", NotificationUtil.SHORT);
            return;
        }
        final List<Car> freeCars = getFreeCars(project);
        if (freeCars.isEmpty()) {
            NotificationUtil.warningAlert("Warning", "All cars are busy", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/EditCarList.fxml"));
        final Parent parent = fxmlLoader.load();
        EditCarListController editCarListController = fxmlLoader.getController();
        editCarListController.loadCars(project, freeCars);
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
            Parent parent = fxmlLoader.load();

            final Stage stage = new Stage();
            Scene value = new Scene(parent);
            value.getStylesheets().add("css/main.css");
            stage.setScene(value);
            stage.initModality(Modality.WINDOW_MODAL);
            Window window = table.getScene().getWindow();
            stage.initOwner(window);
            stage.show();

            stage.setOnHiding(event -> {
                if ((boolean) ControllersDataFactory.getLink().get(AskFormController.class, "isDelete")) {

                    try {
                        ServiceFactory.getProjectService().deleteEntity(project);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
    private List<Instructor> getFreeInstructors(Project project) {
        return ServiceFactory.getWiseacreService().getFreeInstructors(project);
    }

    /**
     * Preparing pdf report for project in table.
     */
    private void pdfReport() {
        if (table.getItems().isEmpty()) {
            NotificationUtil.warningAlert("Warning", "Nothing to export", NotificationUtil.SHORT);
            return;
        }
        ServiceFactory.getPdfService().projectReport(table.getItems());
    }

    /**
     * Preparing excel report for project in table.
     */
    private void excelReport() {
        if (table.getItems().isEmpty()) {
            NotificationUtil.warningAlert("Warning", "Nothing to export", NotificationUtil.SHORT);
            return;
        }
        ServiceFactory.getExcelService().projectReport(table.getItems());
    }

    /**
     * Get free cars.
     */
    private List<Car> getFreeCars(Project project) {
        return ServiceFactory.getWiseacreService().getFreeCar(project);
    }
}
