package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Instructor;
import defencer.model.enums.Role;
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

    private static final int MAX_NUMBER_LENGTH = 10;
    private static final int TEMP_LENGTH = 1;

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
    private JFXTextField telegramId;
    @FXML
    private JFXComboBox<String> role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        role.setItems(FXCollections
                .observableArrayList(Role.CHIEF_OFFICER, Role.COORDINATOR, Role.INSTRUCTOR, Role.OFFICE_MANAGER));
        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());

        btnAddInstructor.setOnAction(e -> prepareAdding());
    }

    /**
     * Prepare {@link Instructor} to creating.
     */
    private void prepareAdding() {

        if (role.getValue() != null && !role.getValue().equals("")
                && firstName.getText().length() != 0
                && email.getText().length() != 0
                && phone.getText().length() != 0 && phone.getText().length() == MAX_NUMBER_LENGTH
                && phone.getText().substring(0, TEMP_LENGTH).equals("0")
                && isOnlyNumber(phone.getText())
                && isOnlyNumber(telegramId.getText())
                && qualification.getText().length() != 0) {

            final Instructor instructor = new Instructor();
            instructor.setFirstName(firstName.getText());
            instructor.setLastName(lastName.getText());
            instructor.setEmail(email.getText());
            instructor.setPhone(phone.getText());
            instructor.setQualification(qualification.getText());
            instructor.setRole(role.getValue());
            instructor.setTelegramId(Long.valueOf(telegramId.getText()));
            create(instructor);
            root.getScene().getWindow().hide();

        } else {
            NotificationUtil.warningAlert("Warning", "Form isn't filled right", NotificationUtil.LONG);
        }
    }

    /**
     * Checking does data consist only from numbers.
     */
    private boolean isOnlyNumber(String data) {
        try {
            Integer.parseInt(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param instructor going to be create.
     */
    private void create(Instructor instructor) {
        try {
            ServiceFactory.getInstructorService().createEntity(instructor);
        } catch (SQLException | EntityAlreadyExistsException e) {
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
    }
}
