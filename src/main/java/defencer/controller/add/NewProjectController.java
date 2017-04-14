package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
    private JFXComboBox<String> comboInstructors;
    @FXML
    private JFXButton btnAddInstructor;
    @FXML
    private JFXTextArea areaInstructors;
    @FXML
    private JFXComboBox<String> comboCars;
    @FXML
    private JFXButton btnAddCar;
    @FXML
    private JFXTextArea areaCars;
    @FXML
    private DatePicker dataFrom;
    @FXML
    private DatePicker dataTo;
    @FXML
    private JFXComboBox<String> projectName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectName.setItems(FXCollections
                .observableArrayList("CLS", "BLS"));

        comboInstructors.setItems(FXCollections
                .observableArrayList("Igor", "Dima", "Nikita"));

        comboCars.setItems(FXCollections
                .observableArrayList("AB 1715", "AB 1212"));

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
    }

    /**
     * Clear form fields after adding.
     */
    private void clear() {
        projectName.setValue("Name of project");
        place.clear();
        description.clear();
        comboInstructors.setPromptText("Instructors");
        areaInstructors.clear();
        comboCars.setPromptText("Car");
        areaCars.clear();
        dataFrom.setPromptText("Date from");
        dataTo.setPromptText("Date to");
    }
}