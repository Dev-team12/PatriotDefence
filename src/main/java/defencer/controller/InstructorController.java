package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.controller.update.UpdateInstructorController;
import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller provides api for creating, getting, editing and deleting instructors.
 *
 * @author Igor Gnes on 4/6/17.
 */
public class InstructorController implements Initializable {

    @FXML
    private TableView<Instructor> table;
    @FXML
    private TableColumn<Instructor, String> firstName;
    @FXML
    private TableColumn<Instructor, String> lastName;
    @FXML
    private TableColumn<Instructor, String> email;
    @FXML
    private TableColumn<Instructor, String> phone;
    @FXML
    private TableColumn<Instructor, String> qualification;
    @FXML
    private TableColumn<Instructor, String> status;
    @FXML
    private TableColumn<Instructor, String> role;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnUpdate;

    private ObservableList<Instructor> observableInstructors = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertTableInstructors();

        loadInstructors();

        btnAddOneMore.setOnAction(e -> newInstructor());

        btnEdit.setOnAction(this::editInstructor);

        btnDelete.setOnAction(e -> deleteInstructor());
    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editInstructor(ActionEvent event) {
        final Instructor instructor = table.getSelectionModel().getSelectedItem();
        if (instructor == null) {
            NotificationUtil.warningAlert("Warning", "Select instructor first", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/update/UpdateInstructor.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateInstructorController updateInstructorController = fxmlLoader.getController();
        updateInstructorController.editCurrentInstructor(instructor);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }

    /**
     * Load instructors into table.
     */
    private void loadInstructors() {
        observableInstructors.addAll(getInstructors());
        table.setItems(observableInstructors);
    }

    /**
     * Insert value for table.
     */
    private void insertTableInstructors() {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        qualification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    /**
     * Deletes selected instructor.
     */
    private void deleteInstructor() {
        final Instructor instructor = table.getSelectionModel().getSelectedItem();
        if (instructor == null) {
            NotificationUtil.warningAlert("Warning", "Select instructor firstly", NotificationUtil.SHORT);
            return;
        }
        try {
            ServiceFactory.getInstructorService().deleteEntity(instructor);
            observableInstructors.clear();
            loadInstructors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return list of instructors.
     */
    private List<Instructor> getInstructors() {
        return ServiceFactory.getInstructorService().getInstructors();
    }

    /**
     * Open new page to add one more instructor.
     *
     * {@link SneakyThrows} here because i am totally sure that path to fxml is correct.
     */
    @SneakyThrows
    private void newInstructor() {
        Parent root = FXMLLoader.load(getClass().getResource("/entity/add/NewInstructor.fxml"));
        final Stage stage = new Stage();
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}