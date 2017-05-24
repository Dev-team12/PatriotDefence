package defencer.controller.update;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Instructor;
import defencer.model.enums.Role;
import defencer.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/16/17.
 */
public class UpdateInstructorController implements Initializable{

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnUpdateInstructor;
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
    @FXML
    private JFXTextField txtTelegramId;

    private Long instructorId;
    private String videoPath;
    private String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        role.setItems(FXCollections
                .observableArrayList(Role.CHIEF_OFFICER, Role.COORDINATOR, Role.INSTRUCTOR, Role.OFFICE_MANAGER));

        btnUpdateInstructor.setOnAction(e -> prepareUpdating());

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
    }

    /**
     * Prepare {@link Instructor} to updating.
     */
    private void prepareUpdating() {
        final Instructor instructor = new Instructor();
        instructor.setFirstName(firstName.getText());
        instructor.setLastName(lastName.getText());
        instructor.setEmail(email.getText());
        instructor.setPhone(phone.getText());
        instructor.setQualification(qualification.getText());
        instructor.setId(instructorId);
        instructor.setRole(role.getValue());
        instructor.setVideoPath(videoPath);
        instructor.setPassword(password);
        instructor.setTelegramId(Long.valueOf(txtTelegramId.getText()));
        update(instructor);
        root.getScene().getWindow().hide();
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
        role.setValue(instructor.getRole());
        instructorId = instructor.getId();
        videoPath = instructor.getVideoPath();
        password = instructor.getPassword();
        txtTelegramId.setText(String.valueOf(instructor.getTelegramId()));
        if (instructor.getTelegramId() == null) {
            txtTelegramId.setText("");
            return;
        }
        txtTelegramId.setText(String.valueOf(instructor.getTelegramId()));
    }

    /**
     * @param instructor going to be update.
     * @return already updated {@link Instructor}.
     */
    private Instructor update(Instructor instructor) {
        try {
            return ServiceFactory.getInstructorService().updateEntity(instructor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
