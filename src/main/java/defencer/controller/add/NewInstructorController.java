package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Instructor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
                .observableArrayList("Chief Operating Officer", "Coordinator", "Instructor"));
        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
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
     * @param instructor is selected {@link Instructor} for set in edit form.
     */
    public void editCurrentInstructor(Instructor instructor) {
        firstName.setText(instructor.getFirstName());
        lastName.setText(instructor.getLastName());
        email.setText(instructor.getEmail());
        phone.setText(instructor.getPhone());
        qualification.setText(instructor.getQualification());
        role.setValue("Coordinator");
    }
}
