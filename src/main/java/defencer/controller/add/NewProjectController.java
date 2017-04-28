package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import defencer.data.CurrentUser;
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
 * @author Igor Gnes on 4/13/17.
 */
public class NewProjectController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField place;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton btnCreate;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        projectName.setItems(FXCollections
                .observableArrayList(getProjectName()));

        comboCars.setItems(FXCollections
                .observableArrayList(getFreeCars()));

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());

        btnCreate.setOnAction(e -> prepareAdding());
    }

    /**
     * Prepare {@link Project} to creating.
     */
    private void prepareAdding() {
        final Project project = new Project();
        project.setName(projectName.getValue());
        project.setDateStart(dataFrom.getValue());
        project.setDateFinish(dataTo.getValue());
        project.setCar(areaCars.getText());
        project.setPlace(place.getText());
        project.setDescription(description.getText());
        project.setAuthor(CurrentUser.getLink().getFirstName());
        project.setDateOfCreation(LocalDate.now());
        create(project);
        root.getScene().getWindow().hide();
    }

    /**
     * Clear form fields after adding.
     */
    private void clear() {
        projectName.setValue("Project name");
        place.clear();
        description.clear();
        comboInstructors.setPromptText("Instructors");
        areaInstructors.clear();
        comboCars.setPromptText("Car");
        areaCars.clear();
        dataFrom.setPromptText("Date from");
        dataTo.setPromptText("Date to");
    }

    /**
     * @param project going to be create.
     * @return already created {@link Project}.
     */
    private Project create(Project project) {
        try {
            ServiceFactory.getProjectService().createEntity(project);
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
