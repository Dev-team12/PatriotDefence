package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
public class    InstructorController implements Initializable {

    @FXML
    private TableView<Instructor> table;
    @FXML
    private TableColumn<Instructor, String> name;
    @FXML
    private TableColumn<Instructor, String> email;
    @FXML
    private TableColumn<Instructor, String> phone;
    @FXML
    private TableColumn<Instructor, String> qualification;
    @FXML
    private TableColumn<Instructor, String> status;
    @FXML
    private JFXComboBox<String> searchBy;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private TextField txtSearch;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnUpdate;

    private ObservableList<Instructor> observableInstructors = FXCollections.observableArrayList();
    private final Stage stage = new Stage();

    private Long instructorId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertTableInstructors();
        loadInstructors();
        searchBy.setPromptText("Name");
        searchBy.setItems(FXCollections
                .observableArrayList("Name", "Email", "Phone", "Qualification"));

        btnFind.setOnAction(e -> System.out.println(searchBy.getValue()));

        btnAddOneMore.setOnAction(e -> newInstructor());
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
        instructor.setStatus("Free");
        List<Instructor> list = new LinkedList<>();
        list.add(instructor);

        observableInstructors.addAll(list);
        table.setItems(observableInstructors);
    }

    /**
     * Insert value for table.
     */
    private void insertTableInstructors() {
        name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        qualification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
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

        try {
            return ServiceFactory.getInstructorService().getEntityForMonths();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return search by selected parameters.
     */
    private List<Instructor> search() {
        try {
            return ServiceFactory.getInstructorService()
                    .searchEntity(searchBy.getValue(), txtSearch.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Open new page to add one more instructor.
     *
     * {@link SneakyThrows} here because i am totally sure that path to fxml is correct.
     */
    @SneakyThrows
    private void newInstructor() {
        Parent root = FXMLLoader.load(getClass().getResource("/NewInstructor.fxml"));
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}