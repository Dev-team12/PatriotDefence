package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.model.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller provides api for creating, getting, editing and deleting instructors.
 *
 * @author Igor Gnes on 4/6/17.
 */
public class InstructorController implements Initializable {


    @FXML
    private TableColumn<Instructor, String> name;
    @FXML
    private TableColumn<Instructor, String> email;
    @FXML
    private TableColumn<Instructor, String> phone;
    @FXML
    private TableColumn<Instructor, String> notes;
    @FXML
    private TableColumn<Instructor, String> qualification;
    @FXML
    private JFXComboBox comboBoxInstructors;
    @FXML
    private TextField textSearch;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnUpdate;

    private ObservableList<Instructor> instructors = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertTableInstructors();
        loadInstructors();
    }

    private void loadInstructors() {
        //todo get and set in ObservableList all instructors from database
    }

    public void editInstructorList() {

    }

    /**
     * Insert value for table.
     */
    private void insertTableInstructors() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        qualification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        name.setCellValueFactory(new PropertyValueFactory<>("note"));
    }
}
