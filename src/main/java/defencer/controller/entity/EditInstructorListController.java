package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import defencer.model.Project;
import defencer.model.Schedule;
import defencer.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 5/2/17.
 */
public class EditInstructorListController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnOk;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private TableView<Schedule> tableCurrentInstructors;
    @FXML
    private TableColumn<Schedule, String> instructorName;

    private ObservableList<Schedule> observableInstructors = FXCollections.observableArrayList();
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnOk.setOnAction(e -> updateData());

        loadInstructorTable();

        btnDelete.setOnAction(e -> delete());
    }

    /**
     * Update project set him list of instructors.
     */
    private void updateData() {
        root.getScene().getWindow().hide();
    }

    /**
     * Delete selected instructor who was selected in project before.
     */
    private void delete() {
        final Schedule schedule = tableCurrentInstructors.getSelectionModel().getSelectedItem();
        if (schedule == null) {
            return;
        }
        try {
            ServiceFactory.getWiseacreService().deleteEntity(schedule);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadInstructors(project);
    }

    /**
     * Load selected instructor table.
     */
    void loadInstructors(Project project) {
        this.project = project;
        observableInstructors.clear();
        observableInstructors.addAll(getCurrentInstructors(project.getId()));
        tableCurrentInstructors.setItems(observableInstructors);
    }

    /**
     * Insert value for current instructor table.
     */
    private void loadInstructorTable() {
        instructorName.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
    }

    /**
     * @return list of instructors who were selected before.
     */
    private List<Schedule> getCurrentInstructors(Long projectId) {
        return ServiceFactory.getWiseacreService().getCurrentInstructors(projectId);
    }
}
