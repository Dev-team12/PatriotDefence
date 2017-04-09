package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
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

    private Long instructorId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertTableInstructors();
        loadInstructors();
        btnDelete.setOnAction(s -> delete());
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

    /**
     * Deletes given entity.
     */
    private void delete() {
        try {
            ServiceFactory.getInstructorService().deleteEntity(instructorId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /**
     * @param instructor going to be create.
     * @return already created {@link Instructor}.
     */
    private Instructor create(Instructor instructor) {
        try {
            return ServiceFactory.getInstructorService().createEntity(instructor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
