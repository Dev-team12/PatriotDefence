package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.controller.add.NewInstructorController;
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
import java.util.LinkedList;
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

    private static final Long ROLE = 12L;

    private Long instructorId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertTableInstructors();
        loadInstructors();

        btnAddOneMore.setOnAction(e -> newInstructor());

        btnEdit.setOnAction(this::editInstructor);
    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editInstructor(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/add/newInstructor.fxml"));
        Parent parent = fxmlLoader.load();
        NewInstructorController newInstructorController = fxmlLoader.getController();

        final Instructor instructor = table.getSelectionModel().getSelectedItem();
        if (instructor == null) {
            NotificationUtil.warningAlert("Warning", "Select instructor firstly", NotificationUtil.SHORT);
            return;
        }
        newInstructorController.editCurrentInstructor(instructor);

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
        final Instructor instructor = new Instructor();
        instructor.setEmail("gmail.com");
        instructor.setQualification("Instructor");
        instructor.setPhone("093");
        instructor.setFirstName("Alex");
        instructor.setLastName("Borchuck");
        instructor.setStatus("Free");


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        instructor.setRole("test");

        List<Instructor> list = new LinkedList<>();
        list.add(instructor);

//        final List<Instructor> instructors = getInstructors();
        observableInstructors.addAll(list);
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

    /**
     * @return list of instructors for last months.
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