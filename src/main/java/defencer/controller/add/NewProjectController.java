package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import defencer.data.CurrentUser;
import defencer.model.Project;
import defencer.model.enums.Role;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private DatePicker dateFrom;
    @FXML
    private DatePicker dataTo;
    @FXML
    private JFXComboBox<String> projectName;

    private static final int PERIOD = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectName.setItems(FXCollections
                .observableArrayList(getProjectName()));

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());

        btnCreate.setOnAction(e -> prepareAdding());
    }

    /**
     * Prepare {@link Project} to creating.
     */
    private void prepareAdding() {
        if (validatorFields()) {
            if (!checkPeriod()) {
                NotificationUtil.warningAlert("Warning", "You can't create the project in less than 5 days from today", NotificationUtil.SHORT);
                return;
            }
            final Project project = new Project();
            project.setName(projectName.getValue());
            project.setDateStart(dateFrom.getValue());
            project.setDateFinish(dataTo.getValue());
            project.setPlace(place.getText());
            project.setDescription(description.getText());
            project.setAuthor(CurrentUser.getLink().getFirstName() + " " + CurrentUser.getLink().getLastName());
            project.setDateOfCreation(LocalDate.now());
            create(project);
            root.getScene().getWindow().hide();
        } else {
            NotificationUtil.warningAlert("Warning", "Form isn't filled right.", NotificationUtil.LONG);
        }
    }

    private boolean validatorFields() {
        return projectName.getValue() != null && !projectName.getValue().equals("")
                && dateFrom.getValue() != null
                && dataTo.getValue() != null
                && description.getText().length() != 0;
    }

    private boolean checkPeriod() {
        return Role.CHIEF_OFFICER.equals(CurrentUser.getLink().hasRole()) || ChronoUnit.DAYS.between(LocalDate.now(), dateFrom.getValue()) >= PERIOD;
    }

    /**
     * Clear form fields after adding.
     */
    private void clear() {
        projectName.setValue("Project name");
        place.clear();
        description.clear();
        dateFrom.setPromptText("Date from");
        dataTo.setPromptText("Date to");
    }

    /**
     * @param project going to be create.
     * @return already created {@link Project}.
     */
    private Project create(Project project) {
        try {
            return ServiceFactory.getProjectService().createEntity(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return all type of available projects.
     */
    private List<String> getProjectName() {
        return ServiceFactory.getWiseacreService().getAvailableProject();
    }
}
