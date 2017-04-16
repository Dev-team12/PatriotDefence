package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Apprentice;
import defencer.service.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
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
    private JFXTextField projectName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());

        btnCreate.setOnAction(e -> prepareAdding());
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

    private void prepareAdding() {

        final Apprentice apprentice = new Apprentice();
        apprentice.setName(firstName.getText());
        apprentice.setEmail(email.getText());
        apprentice.setPhone(phone.getText());
        apprentice.setOccupation(occupation.getText());
        apprentice.setNameOfProject(projectName.getText());// todo select getValue(); from combo box
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
