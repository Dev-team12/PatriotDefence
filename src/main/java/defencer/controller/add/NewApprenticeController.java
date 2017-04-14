package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Apprentice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/12/17.
 */
public class NewApprenticeController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnAddPupil;
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
    private JFXTextField projectName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
    }

    /**
     * Clear form fields after adding.
     */
    private void clear() {
        firstName.clear();
        email.clear();
        phone.clear();
        occupation.clear();
        projectName.clear();
    }

    /**
     * @param apprentice is selected {@link Apprentice} for set in edit form.
     */
    public void editCurrentApprentice(Apprentice apprentice) {
        firstName.setText(apprentice.getName());
        email.setText(apprentice.getEmail());
        phone.setText(apprentice.getPhone());
        occupation.setText(apprentice.getOccupation());
        projectName.setText(apprentice.getNameOfProject());
    }
}
