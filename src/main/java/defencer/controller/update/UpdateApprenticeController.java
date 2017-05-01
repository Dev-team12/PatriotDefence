package defencer.controller.update;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Apprentice;
import defencer.service.factory.ServiceFactory;
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
 * @author Igor Gnes on 4/16/17.
 */
public class UpdateApprenticeController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnUpdate;
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

    private Long apprenticeId;
    private LocalDate localDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectName.setItems(FXCollections
                .observableArrayList(getProjectName()));

        btnUpdate.setOnAction(e -> prepareUpdating());

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
    }

    /**
     * Prepare {@link Apprentice} to updating.
     */
    private void prepareUpdating() {
        final Apprentice apprentice = new Apprentice();
        apprentice.setName(firstName.getText());
        apprentice.setEmail(email.getText());
        apprentice.setPhone(phone.getText());
        apprentice.setOccupation(occupation.getText());
        apprentice.setProjectName(projectName.getValue());
        apprentice.setId(apprenticeId);
        apprentice.setDateOfAdded(localDate.plusDays(1));
        update(apprentice);
        root.getScene().getWindow().hide();
    }

    /**
     * @param apprentice is selected {@link Apprentice} for set in edit form.
     */
    public void editCurrentApprentice(Apprentice apprentice) {
        firstName.setText(apprentice.getName());
        email.setText(apprentice.getEmail());
        phone.setText(apprentice.getPhone());
        occupation.setText(apprentice.getOccupation());
        projectName.setValue(apprentice.getProjectName());
        apprenticeId = apprentice.getId();
        localDate = apprentice.getDateOfAdded();
    }

    /**
     * @param apprentice going to be update.
     * @return already updated {@link Apprentice}.
     */
    private Apprentice update(Apprentice apprentice) {
        try {
            return ServiceFactory.getApprenticeService().updateEntity(apprentice);
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
