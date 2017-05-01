package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Apprentice;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/12/17.
 */
public class NewApprenticeController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnCreate;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField occupation;
    @FXML
    private JFXComboBox<String> projectName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectName.setItems(FXCollections
                .observableArrayList(getProjectName()));

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());

        btnCreate.setOnAction(e -> prepareAdding());
    }

    /**
     * Clear form fields after adding.
     */
    private void clear() {
        firstName.clear();
        email.clear();
        phone.clear();
        occupation.clear();
        projectName.setPromptText("Project name");
    }

    /**
     * Prepare {@link Apprentice} to creating.
     */
    private void prepareAdding() {
        final Apprentice apprentice = new Apprentice();
        apprentice.setName(firstName.getText());
        apprentice.setEmail(email.getText());
        apprentice.setPhone(phone.getText());
        apprentice.setOccupation(occupation.getText());
        apprentice.setProjectName(projectName.getValue());
        apprentice.setDateOfAdded(LocalDate.now().plusDays(1));
        create(apprentice);
        root.getScene().getWindow().hide();
    }

    /**
     * @param apprentice going to be create.
     * @return already created {@link Apprentice}.
     */
    private Apprentice create(Apprentice apprentice) {
        try {
            return ServiceFactory.getApprenticeService().createEntity(apprentice);
        }  catch (SQLException | EntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
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
