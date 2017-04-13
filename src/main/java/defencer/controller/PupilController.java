package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.model.Pupil;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/12/17.
 */
public class PupilController implements Initializable {

    @FXML
    private TableView<Pupil> table;
    @FXML
    private TableColumn<Pupil, String> name;
    @FXML
    private TableColumn<Pupil, String> email;
    @FXML
    private TableColumn<Pupil, String> phone;
    @FXML
    private TableColumn<Pupil, String> occupation;
    @FXML
    private TableColumn<Pupil, String> project;
    @FXML
    private JFXComboBox<String> searchBy;
    @FXML
    private TextField txtSearch;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnUpdate;

    private ObservableList<Pupil> observablePupils = FXCollections.observableArrayList();

    private final Stage stage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertPupilTable();
        loadPupils();
        searchBy.setPromptText("Name");
        searchBy.setItems(FXCollections
                .observableArrayList("Name", "Email", "Phone", "Project", "Occupation"));

        btnFind.setOnAction(e -> System.out.println(searchBy.getValue()));

        btnAddOneMore.setOnAction(e -> newPupil());
    }

    /**
     * Load instructors into table.
     */
    private void loadPupils() {
        final Pupil pupil = new Pupil();
        pupil.setName("Alex");
        pupil.setEmail("gmail.com");
        pupil.setOccupation("Pupil");
        pupil.setPhone("093");
        pupil.setNameOfProject("CLS");
        List<Pupil> list = new LinkedList<>();
        list.add(pupil);

        observablePupils.addAll(list);
        table.setItems(observablePupils);
    }

    /**
     * Insert value for table.
     */
    private void insertPupilTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        occupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        project.setCellValueFactory(new PropertyValueFactory<>("nameOfProject"));
    }

    /**
     * Open new page to add one more pupil.
     *
     * {@link SneakyThrows} here because i am totally sure that path to fxml id correct.
     */
    @SneakyThrows
    private void newPupil() {
        Parent root = FXMLLoader.load(getClass().getResource("/NewPupil.fxml"));
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
