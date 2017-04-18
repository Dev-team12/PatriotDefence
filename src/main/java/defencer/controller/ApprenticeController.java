package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.controller.update.UpdateApprenticeController;
import defencer.model.Apprentice;
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
    private JFXComboBox<String> comboProject;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEdit;

    private ObservableList<Apprentice> observableApprentices = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertApprenticeTable();
        loadApprentice();

        comboProject.setItems(FXCollections
                .observableArrayList(getProjectName()));

        btnAddOneMore.setOnAction(e -> newPupil());

        btnEdit.setOnAction(this::editApprentice);

        btnDelete.setOnAction(e -> deleteApprentice());
    }

    /**
     * Load instructors into table.
     */
    private void loadApprentice() {
        observableApprentices.addAll(getApprentice());
        table.setItems(observableApprentices);
    }

    /**
     * Insert value for table.
     */
    private void insertApprenticeTable() {
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
        final Stage stage = new Stage();
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editApprentice(ActionEvent event) {
        final Apprentice apprentice = table.getSelectionModel().getSelectedItem();
        if (apprentice == null) {
            NotificationUtil.warningAlert("Warning", "Select apprentice firstly", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/update/UpdateApprentice.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateApprenticeController updateApprenticeController = fxmlLoader.getController();
        updateApprenticeController.editCurrentApprentice(apprentice);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }

    /**
     * @return list of apprentice for last months.
     */
    private List<Apprentice> getApprentice() {
        return ServiceFactory.getApprenticeService().getApprenticeLastMonths();
    }

    /**
     * Deletes selected apprentice.
     */
    private void deleteApprentice() {
        final Apprentice apprentice = table.getSelectionModel().getSelectedItem();
        if (apprentice == null) {
            NotificationUtil.warningAlert("Warning", "Select apprentice firstly", NotificationUtil.SHORT);
            return;
        }
        try {
            ServiceFactory.getApprenticeService().deleteEntity(apprentice);
            observableApprentices.clear();
            loadApprentice();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return all type of available projects.
     */
    private List<String> getProjectName() {
        return ServiceFactory.getWiseacreService().getAvailableProject();
    }
}
