package defencer.controller.update;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Project;
import defencer.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/16/17.
 */
public class UpdateProjectController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField place;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXComboBox<String> comboInstructors;
    @FXML
    private JFXButton btnAddInstructor;
    @FXML
    private JFXTextArea areaInstructors;
    @FXML
    private JFXComboBox<String> comboCars;
    @FXML
    private JFXButton btnAddCar;
    @FXML
    private JFXTextArea areaCars;
    @FXML
    private DatePicker dataFrom;
    @FXML
    private DatePicker dataTo;
    @FXML
    private JFXComboBox<String> projectName;

    private Long projectId;
    private LocalDate localDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        projectName.setItems(FXCollections
                .observableArrayList(getProjectName()));

        comboCars.setItems(FXCollections
                .observableArrayList(getFreeCars()));

        btnUpdate.setOnAction(e -> prepareUpdating());

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
    }

    /**
     * Prepare {@link Project} to updating.
     */
    private void prepareUpdating() {
        final Project project = new Project();
        project.setName(projectName.getValue());
        project.setDateStart(dataFrom.getValue());
        project.setDateFinish(dataTo.getValue());
        project.setCar(areaCars.getText());
        project.setPlace(place.getText());
        project.setDescription(description.getText());
        project.setAuthor("Igor"); // todo current user getFirstName
        project.setId(projectId);
        project.setDateOfCreation(localDate);
        update(project);
        root.getScene().getWindow().hide();
    }

    /**
     * @param project is selected {@link Project} for set in edit form.
     */
    public void editCurrentProject(Project project) {
        projectName.setValue(project.getName());
        place.setText(project.getPlace());
        description.setText(project.getDescription());
        dataFrom.setValue(project.getDateStart());
        dataTo.setValue(project.getDateFinish());
        areaCars.setText(project.getCar());
        projectId = project.getId();
        localDate = project.getDateOfCreation();
    }

    /**
     * @param project going to be update.
     *
     * @return already updated {@link Project}.
     */
    private Project update(Project project) {
        try {
            return ServiceFactory.getProjectService().updateEntity(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return free car's name for project.
     */
    private List<String> getFreeCars() {
        final List<String> freeCar = ServiceFactory.getWiseacreService().getFreeCar();
        if (freeCar.isEmpty()) {
            comboCars.setPromptText("Cars are busy");
        }
        return freeCar;
    }

    /**
     * @return all type of available projects.
     */
    private List<String> getProjectName() {
        return ServiceFactory.getWiseacreService().getAvailableProject();
    }
}
