package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
                .observableArrayList("Admin", "Kordunator", "User"));
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
}
