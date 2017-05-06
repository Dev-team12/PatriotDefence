package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import defencer.model.Instructor;
import defencer.model.Project;
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
    private TableView<Instructor> tableCurrentInstructors;
    @FXML
    private TableColumn<Instructor, String> instructorName;

    private ObservableList<Instructor> observableInstructors = FXCollections.observableArrayList();
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
        final Instructor instructor = tableCurrentInstructors.getSelectionModel().getSelectedItem();
        if (instructor == null) {
            return;
        }
        tableCurrentInstructors.getItems().remove(instructor);
        ServiceFactory.getWiseacreService().deleteSelectedInstructors(instructor.getId(), tableCurrentInstructors.getItems(), project);
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
        instructorName.setCellValueFactory(new PropertyValueFactory<>("firstLastName"));
    }

    /**
     * @return list of instructors who were selected before.
     */
    private List<Instructor> getCurrentInstructors(Long projectId) {
        return ServiceFactory.getWiseacreService().getCurrentInstructors(projectId);
    }
}
