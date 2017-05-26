package defencer.controller.update;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Project;
import defencer.service.CryptoService;
import defencer.service.cryptography.CryptoProject;
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
    private JFXTextArea expectedAndRefusal;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private DatePicker dataFrom;
    @FXML
    private DatePicker dataTo;
    @FXML
    private JFXComboBox<String> projectName;

    private Long projectId;
    private LocalDate localDate;
    private String author;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        projectName.setItems(FXCollections
                .observableArrayList(getProjectName()));

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
        project.setPlace(place.getText());
        project.setDescription(description.getText());
        project.setAuthor(author);
        project.setId(projectId);
        project.setDateOfCreation(localDate);
        update(project);
        root.getScene().getWindow().hide();
    }

    /**
     * @param project is selected {@link Project} for set in edit form.
     *
     */
    public void editCurrentProject(Project project) {
        projectName.setValue(project.getName());
        place.setText(project.getPlace());
        description.setText(project.getDescription());
        dataFrom.setValue(project.getDateStart());
        dataTo.setValue(project.getDateFinish());
        projectId = project.getId();
        localDate = project.getDateOfCreation();
        author = project.getAuthor();
        expectedAndRefusal.setText("Expected: "
                + "\n"
                + project.getExpected()
                + "\n"
                + "\n"
                + "Refusal: "
                + "\n"
                + project.getRefusal()
                + "\n"
                + "\n"
                + "Cars: "
                + "\n"
                + project.getCars());
    }

    /**
     * @param project going to be update.
     */
    private void update(Project project) {
        try {
            CryptoService<Project> cryptoService = new CryptoProject();
            ServiceFactory.getProjectService()
                    .updateEntity(cryptoService.encryptEntity(project));
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
