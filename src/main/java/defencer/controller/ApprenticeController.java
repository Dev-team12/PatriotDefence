package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.controller.add.NewApprenticeController;
import defencer.model.Apprentice;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/12/17.
 */
public class ApprenticeController implements Initializable {

    @FXML
    private TableView<Apprentice> table;
    @FXML
    private TableColumn<Apprentice, String> name;
    @FXML
    private TableColumn<Apprentice, String> email;
    @FXML
    private TableColumn<Apprentice, String> phone;
    @FXML
    private TableColumn<Apprentice, String> occupation;
    @FXML
    private TableColumn<Apprentice, String> project;
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

    private ObservableList<Apprentice> observableApprentices = FXCollections.observableArrayList();
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
        final Apprentice apprentice = new Apprentice();
        apprentice.setName("Alex");
        apprentice.setEmail("gmail.com");
        apprentice.setOccupation("Apprentice");
        apprentice.setPhone("093");
        apprentice.setNameOfProject("CLS");
        List<Apprentice> list = new LinkedList<>();
        list.add(apprentice);

        observableApprentices.addAll(list);
        table.setItems(observableApprentices);
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
        Parent root = FXMLLoader.load(getClass().getResource("/entity/add/NewApprentice.fxml"));
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    public void editApprentice(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/add/NewApprentice.fxml"));
        Parent parent = fxmlLoader.load();
        NewApprenticeController newApprenticeController = fxmlLoader.getController();

        final Apprentice apprentice = table.getSelectionModel().getSelectedItem();

        newApprenticeController.editCurrentApprentice(apprentice);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }
}
