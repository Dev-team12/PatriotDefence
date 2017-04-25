package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/12/17.
 */
public class NewInstructorController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnAddInstructor;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField qualification;
    @FXML
    private JFXComboBox<String> role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        role.setItems(FXCollections
                .observableArrayList("Chief Officer", "Coordinator", "Instructor"));
        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());

        btnAddInstructor.setOnAction(e -> prepareAdding());
    }

    /**
     * Prepare {@link Instructor} to creating.
     */
    private void prepareAdding() {
        final Instructor instructor = new Instructor();
        instructor.setFirstName(firstName.getText());
        instructor.setLastName(lastName.getText());
        instructor.setEmail(email.getText());
        instructor.setPhone(phone.getText());
        instructor.setQualification(qualification.getText());
        instructor.setRole(role.getValue());
        instructor.setStatus("FREE");
        create(instructor);
        root.getScene().getWindow().hide();
    }

    /**
     * Clear form fields after adding.
     */
    private void clear() {
        firstName.clear();
        lastName.clear();
        email.clear();
        phone.clear();
        qualification.clear();
        role.setPromptText("Role");
    }

    /**
     * @param instructor going to be create.
     * @return already created {@link Instructor}.
     */
    private Instructor create(Instructor instructor) {
        try {
            return ServiceFactory.getInstructorService().createEntity(instructor);
        } catch (SQLException | EntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
        return null;
    }
}
