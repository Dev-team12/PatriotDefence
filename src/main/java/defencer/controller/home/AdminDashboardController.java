package defencer.controller.home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.data.CurrentUser;
import defencer.model.AvailableProject;
import defencer.model.Car;
import defencer.model.enums.Role;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.val;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Igor Gnes on 4/13/17.
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private TableView<AvailableProject> tableProject;
    @FXML
    private TableColumn<AvailableProject, String> projectName;
    @FXML
    private TableView<Car> tableCar;
    @FXML
    private TableColumn<Car, String> car;
    @FXML
    private JFXButton btnDeleteCar;
    @FXML
    private JFXButton btnDeleteProject;
    @FXML
    private JFXButton btnAddCar;
    @FXML
    private JFXButton btnCreateProject;
    @FXML
    private JFXTextField txtProject;
    @FXML
    private JFXTextField txtCar;
    @FXML
    private BarChart<String, Long> barChartForWorkDays;
    @FXML
    private BarChart<String, Integer> barChartForProjects;
    @FXML
    private Text totalInstructors;
    @FXML
    private Text totalApprentice;
    @FXML
    private Text projectForMonths;
    @FXML
    private Text apprenticeForMonths;

    private ObservableList<AvailableProject> observableProject = FXCollections.observableArrayList();
    private ObservableList<Car> observableCar = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        insertTable();
        loadDataForProject();
        loadDataForCar();
        configureTitles();
        configureWorkDay();
        configureProject();

        btnAddCar.setOnAction(s -> createCar());
        btnCreateProject.setOnAction(e -> createProject());
        btnDeleteProject.setOnAction(s -> deleteProject());
        btnDeleteCar.setOnAction(s -> deleteCar());
    }

    /**
     * Load projects' names in table.
     */
    private void loadDataForProject() {
        observableProject.addAll(getProjectNames());
        tableProject.setItems(observableProject);
    }

    /**
     * Load cars' names in table.
     */
    private void loadDataForCar() {
        observableCar.addAll(getCarNames());
        tableCar.setItems(observableCar);
    }

    /**
     * @return list of cars' names, status and id.
     */
    private List<Car> getCarNames() {
        return ServiceFactory.getWiseacreService().getCarForAdminDashboard();
    }

    /**
     * @return list of project' names and id.
     */
    private List<AvailableProject> getProjectNames() {
        return ServiceFactory.getWiseacreService().getProjectForAdminDashboard();
    }

    /**
     * Insert values for table.
     */
    private void insertTable() {
        projectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        car.setCellValueFactory(new PropertyValueFactory<>("carName"));
    }

    /**
     * Configured titles with statistic for last months.
     */
    private void configureTitles() {
        totalInstructors.setText("Total instructors: " + getTotalInstructors());
        totalApprentice.setText("Total Apprentice: " + getTotalApprentice());
        projectForMonths.setText("Projects for months: " + getProjectForLastMonths());
        apprenticeForMonths.setText("Apprentice for months: " + getApprenticeForLastMonths());
    }

    /**
     * Configured bar chart with instructors' work days.
     */
    private void configureWorkDay() {

        final XYChart.Series<String, Long> workDaysStatistic = new XYChart.Series<>();
        workDaysStatistic.setName("Instructors' work days");
        getInstructorStatisticForAdminDashBoard().forEach((s, b) -> {
            final XYChart.Data<String, Long> data = new XYChart.Data<>(s, b);
            workDaysStatistic.getData().add(data);
        });
        barChartForWorkDays.setTitle("Instructors' work days");
        barChartForWorkDays.getData().add(workDaysStatistic);
    }

    /**
     * Configured bar chart with projects statistic for months.
     */
    private void configureProject() {

        final XYChart.Series<String, Integer> projectStatistic = new XYChart.Series<>();
        projectStatistic.setName("Projects statistic for months");
        getProjectStatisticForAdminDashBoard().forEach((s, b) -> {
            final XYChart.Data<String, Integer> data = new XYChart.Data<>(s, b);
            projectStatistic.getData().add(data);
        });
        barChartForProjects.setTitle("Projects statistic for months");
        barChartForProjects.getData().add(projectStatistic);
    }

    /**
     * @return all type of available projects and how many time they were created for last months.
     */
    private Map<String, Integer> getProjectStatisticForAdminDashBoard() {
        return ServiceFactory.getWiseacreService().getProjectStatistic();
    }

    /**
     * @return all instructors and how many time they worked for last months.
     */
    private Map<String, Long> getInstructorStatisticForAdminDashBoard() {
        return ServiceFactory.getWiseacreService().getInstructorStatistic();
    }

    /**
     * @return value of total instructors.
     */
    private int getTotalInstructors() {
        return ServiceFactory.getWiseacreService().getTotalInstructors();
    }

    /**
     * @return value of total apprentice.
     */
    private int getTotalApprentice() {
        return ServiceFactory.getWiseacreService().getTotalApprentice();
    }

    /**
     * @return value of created project for last months.
     */
    private int getProjectForLastMonths() {
        return ServiceFactory.getWiseacreService().getQuantityProjectForLastMonths();
    }

    /**
     * @return value of added apprentice for last months.
     */
    private int getApprenticeForLastMonths() {
        return ServiceFactory.getWiseacreService().getQuantityApprenticeForLastMon();
    }

    /**
     * Create new car.
     */
    private void createCar() {
        if (!Role.CHIEF_OFFICER.equals(CurrentUser.getLink().hasRole())) {
            NotificationUtil.warningAlert("Wrong", "Only Chief officer can do this", NotificationUtil.SHORT);
            return;
        }
        if ("".equals(txtCar.getText())) {
            NotificationUtil.warningAlert("Wrong", "Please enter name", NotificationUtil.SHORT);
            return;
        }
        final Car car = new Car();
        car.setCarName(txtCar.getText());
        try {
            ServiceFactory.getWiseacreService().createCar(car);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
        txtCar.clear();
        observableCar.clear();
        loadDataForCar();
    }

    /**
     * Create new Available project.
     */
    private void createProject() {
        if (!Role.CHIEF_OFFICER.equals(CurrentUser.getLink().hasRole())) {
            NotificationUtil.warningAlert("Wrong", "Only Chief officer can do this", NotificationUtil.SHORT);
            return;
        }
        if ("".equals(txtProject.getText())) {
            NotificationUtil.warningAlert("Wrong", "Please enter name", NotificationUtil.SHORT);
            return;
        }
        val availableProject = new AvailableProject();
        availableProject.setProjectName(txtProject.getText());
        try {
            ServiceFactory.getWiseacreService().createProject(availableProject);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
        txtProject.clear();
        observableProject.clear();
        loadDataForProject();
    }

    /**
     * Delete selected car.
     */
    private void deleteCar() {
        final Car car = tableCar.getSelectionModel().getSelectedItem();
        if (!Role.CHIEF_OFFICER.equals(CurrentUser.getLink().hasRole())) {
            NotificationUtil.warningAlert("Wrong", "Only Chief officer can do this", NotificationUtil.SHORT);
            return;
        }
        if (car == null) {
            NotificationUtil.warningAlert("Warning", "Select car first", NotificationUtil.SHORT);
            return;
        }
        try {
            ServiceFactory.getWiseacreService().deleteCar(car);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
        observableCar.clear();
        loadDataForCar();
    }

    /**
     * Delete selected project.
     */
    private void deleteProject() {
        if (!Role.CHIEF_OFFICER.equals(CurrentUser.getLink().hasRole())) {
            NotificationUtil.warningAlert("Wrong", "Only Chief officer can do this", NotificationUtil.SHORT);
            return;
        }
        final AvailableProject project = tableProject.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project first", NotificationUtil.SHORT);
            return;
        }
        try {
            ServiceFactory.getWiseacreService().deleteProject(project);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
        observableProject.clear();
        loadDataForProject();
    }
}
