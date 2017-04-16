package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.controller.add.NewProjectController;
import defencer.model.Project;
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
import java.util.LinkedList;
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
    private JFXComboBox<String> searchBy;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEdit;

    private ObservableList<Project> observableProjects = FXCollections.observableArrayList();
    private static final int TEST = 12;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertProjectTable();
        loadProjects();
        searchBy.setItems(FXCollections
                .observableArrayList("СLS", "LRPM", "CLSI", "UTLS", "BLS", "EMR", "IDC"));

        btnFind.setOnAction(e -> System.out.println(searchBy.getValue()));

        btnAddOneMore.setOnAction(e -> newProject());

        btnEdit.setOnAction(this::editProject);
    }

    /**
     * Insert value for table.
     */
    private void insertProjectTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataStart.setCellValueFactory(new PropertyValueFactory<>("dataFrom"));
        dataFinish.setCellValueFactory(new PropertyValueFactory<>("dataTo"));
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
        final Project project = new Project();
        project.setName("CLS");
        project.setPlace("Rivne");
        project.setDataFrom("2017-04-05");
        project.setDataTo("2017-04-12");
        project.setCar("Bysik");
        project.setDescription("Mega Project");
        project.setAuthor("Alex");
        List<Project> list = new LinkedList<>();
        list.add(project);

        observableProjects.addAll(list);
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

        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/add/NewProject.fxml"));
        Parent parent = fxmlLoader.load();
        NewProjectController newProjectController = fxmlLoader.getController();

        final Project project = table.getSelectionModel().getSelectedItem();
        if (project == null) {
            NotificationUtil.warningAlert("Warning", "Select project firstly", NotificationUtil.SHORT);
            return;
        }
        newProjectController.editCurrentProject(project);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }
}
